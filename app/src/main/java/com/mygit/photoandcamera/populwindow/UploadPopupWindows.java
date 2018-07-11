package com.mygit.photoandcamera.populwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.mygit.photoandcamera.R;
import com.mygit.photoandcamera.utils.SDUtils;

import java.io.File;

/**
 * Created by admin on 2015/11/26.
 */
public class UploadPopupWindows extends PopupWindow {

    public final static int REQ_CHOOSE_PHOTO_CODE = 1;
    public final static int REQ_CHOOSE_CAMERA_CODE = 2;
    public Uri cameraFileUri;
    Uri cameraUri = null;
    @SuppressWarnings("deprecation")
    private Activity mContext;
    private String filePath;

    public UploadPopupWindows(Context context, View parent) {
        mContext = (Activity) context;
        View view = View.inflate(mContext,
                R.layout.view_upload_popup, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.popup_fade_ins));
        LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_photo));
        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 指定调用相机拍照后照片的储存路径
                    filePath = SDUtils.AppCorpDirPath + System.currentTimeMillis() + "_camera.png";
                    File file = new File(filePath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    cameraUri = Uri.fromFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的方向
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                mContext.startActivityForResult(intent, REQ_CHOOSE_CAMERA_CODE);
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                mContext.startActivityForResult(intent, REQ_CHOOSE_CAMERA_CODE);
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                mContext.startActivityForResult(intent, REQ_CHOOSE_PHOTO_CODE);
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public String getFilePath() {
        return filePath;
    }

    public Uri getUri() {
        return cameraUri;
    }
}
