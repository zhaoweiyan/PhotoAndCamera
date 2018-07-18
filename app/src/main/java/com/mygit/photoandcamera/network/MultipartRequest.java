package com.mygit.photoandcamera.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipartRequest extends Request<JSONObject> {

    private final static String TAG = "MultipartRequest";
    private MultipartEntity entity = new MultipartEntity();

    private final Response.Listener<JSONObject> mListener;

    private List<File> mFileParts;
    private String mFilePartName;
    private Map<String, String> mParams;

    /**
     * 单个文件
     *
     * @param url
     * @param errorListener
     * @param listener
     * @param filePartName
     * @param file
     * @param params
     */
    public MultipartRequest(String url, String filePartName, File file,
                            Map<String, String> params, Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);

        mFileParts = new ArrayList<File>();
        if (file != null) {
            mFileParts.add(file);
        }
        mFilePartName = filePartName;
        mListener = listener;
        mParams = params;
        buildMultipartEntity();
    }

    /**
     * 多个文件，对应一个key
     *
     * @param url
     * @param errorListener
     * @param listener
     * @param filePartName
     * @param files
     * @param params
     */
    public MultipartRequest(String url, String filePartName, List<File> files, Map<String, String> params,
                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mFilePartName = filePartName;
        mListener = listener;
        mFileParts = files;
        mParams = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        if (mFileParts != null && mFileParts.size() > 0) {
            for (File file : mFileParts) {
                entity.addPart(mFilePartName, new FileBody(file));
            }
            long l = entity.getContentLength();
            Log.d(TAG, mFileParts.size() + "个，长度：" + l);
        }

        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    entity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Log.d(TAG, "parseNetworkResponse");
        if (VolleyLog.DEBUG) {
            if (response.headers != null) {
                for (Map.Entry<String, String> entry : response.headers
                        .entrySet()) {
                    VolleyLog.d(entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        try {
            String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        } catch (JSONException var4) {
            return Response.error(new ParseError(var4));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        return headers;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
