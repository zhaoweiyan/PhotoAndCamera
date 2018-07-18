package com.mygit.photoandcamera.network.sevices;

import android.content.Context;

import com.mygit.photoandcamera.models.CodeModel;
import com.mygit.photoandcamera.models.ModelBase;
import com.mygit.photoandcamera.network.MyProjectApi;
import com.mygit.photoandcamera.utils.LogUtil;
import com.mygit.photoandcamera.utils.Utils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关接口
 * Created by admin on 2015/11/3.
 */
public class UserService {

    private static UserService sInstance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (sInstance == null) {
            sInstance = new UserService();
        }
        return sInstance;
    }
    /**
     * 短信发送
     *
     * @param mobile   手机号（限制11位数字 1开头）
     *                 type     type=1 注册， type=2 找回密码……
     * @param callBack
     */
    public void smsSend(Context mContext, String mobile, ModelBase.OnResult callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("type", "1");
        params.put("version", "6.1.0");
        params.put("phoneType", "android");
        JSONObject json = new JSONObject(params);
        MyProjectApi.getInstance().buildJsonRequest("请求url", json, CodeModel.class, callBack);
    }

    /**
     * 退款申请
     *
     * @param callBack
     */
    public void upLoadcommit(Context mContext, String str, List<File> files, ModelBase.OnResult callBack) {

        Map<String, String> params = new HashMap<String, String>();

        params.put("orderNo", str);

        params.put("version", Utils.getAppVersion(mContext));
        params.put("phoneType", "android");
        JSONObject json = new JSONObject(params);

        for (int i = 0; i < files.size(); i++) {
            LogUtil.e("file path==" + files.get(i).getAbsolutePath());
        }
        LogUtil.e("refundCommit request====" + json);

        MyProjectApi.getInstance().buildMultipartRequest("上传的url", "uploadFile", files, params, CodeModel.class, callBack);

    }
}


