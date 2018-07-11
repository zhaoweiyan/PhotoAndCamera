package com.mygit.photoandcamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mygit.photoandcamera.models.ModelBase;
import com.mygit.photoandcamera.network.sevices.UserService;
import com.mygit.photoandcamera.permission.InterfacePermissionResult;
import com.mygit.photoandcamera.permission.PermissionUtils;
import com.mygit.photoandcamera.populwindow.ImageWindow;
import com.mygit.photoandcamera.populwindow.UploadPopupWindows;
import com.mygit.photoandcamera.utils.BitmapUtils;
import com.mygit.photoandcamera.utils.FileUtil;
import com.mygit.photoandcamera.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements InterfacePermissionResult {
    @Bind(R.id.rl_upload_first)
    RelativeLayout rl_upload_first;
    @Bind(R.id.image_first)
    ImageView image_first;
    @Bind(R.id.iv_close_first)
    ImageView iv_close_first;

    @Bind(R.id.rl_upload_second)
    RelativeLayout rl_upload_second;
    @Bind(R.id.image_second)
    ImageView image_second;
    @Bind(R.id.iv_close_second)
    ImageView iv_close_second;

    @Bind(R.id.rl_upload_third)
    RelativeLayout rl_upload_third;
    @Bind(R.id.image_third)
    ImageView image_third;
    @Bind(R.id.iv_close_third)
    ImageView iv_close_third;

    @Bind(R.id.rl_upload_button)
    RelativeLayout rl_upload_button;


    List<File> shopFaceList = new ArrayList<>();
    private MainActivity mContext;
    private PermissionUtils permissionUtils;
    private UploadPopupWindows uploadPopupWindows;
    private ImageWindow imageWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initPremission();
        imageWindow = new ImageWindow(mContext);

        //请求网络接口，根据实际情况编写(会出现异常，因为url缺失)
//        UserService.getInstance().smsSend(this, "电话号码", smsCodeCallback);

    }

    private void initPremission() {
        permissionUtils = new PermissionUtils(getApplicationContext(), mContext);
        permissionUtils.setInterfacePermissionResult(this);
    }

    @OnClick(R.id.rl_upload_button)
    public void uploadImage() {
        permissionUtils.checkPermission(Manifest.permission.CAMERA, 100);
        //这个是直接走状态
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionUtils.showDeniedDialog("您没有相机权限，是否打开");
            return;
        }
        permissionUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 200);
        //这个是直接走状态
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionUtils.showDeniedDialog("您没有存储权限，是否打开");
            return;
        }
        uploadPopupWindows = new UploadPopupWindows(mContext, rl_upload_button);
    }

    @OnClick(R.id.iv_close_first)
    public void deleteFirst() {
        if (shopFaceList.size() > 0) {
            shopFaceList.remove(0);
            showFace();
        }
    }

    @OnClick(R.id.iv_close_second)
    public void deleteSecond() {
        if (shopFaceList.size() > 1) {
            shopFaceList.remove(1);
            showFace();
        }
    }

    @OnClick(R.id.iv_close_third)
    public void deleteThird() {
        if (shopFaceList.size() > 2) {
            shopFaceList.remove(2);
            showFace();
        }
    }

    @OnClick(R.id.image_first)
    public void largeImageFirst() {
        imageWindow.showFile(image_first, shopFaceList, 0);
    }

    @OnClick(R.id.image_second)
    public void largeImageSecond() {
        imageWindow.showFile(image_second, shopFaceList, 1);
    }

    @OnClick(R.id.image_third)
    public void largeImageThird() {
        imageWindow.showFile(image_third, shopFaceList, 2);
    }

    private ModelBase.OnResult smsCodeCallback = new ModelBase.OnResult() {
        @Override
        public void OnListener(ModelBase model) {

        }
    };

    private void showFace() {
        if (shopFaceList.size() == 3) {
            rl_upload_first.setVisibility(View.VISIBLE);
            rl_upload_second.setVisibility(View.VISIBLE);
            rl_upload_third.setVisibility(View.VISIBLE);
            rl_upload_button.setVisibility(View.GONE);
        } else if (shopFaceList.size() == 2) {
            rl_upload_first.setVisibility(View.VISIBLE);
            rl_upload_second.setVisibility(View.VISIBLE);
            rl_upload_third.setVisibility(View.GONE);
            rl_upload_button.setVisibility(View.VISIBLE);
        } else if (shopFaceList.size() == 1) {
            rl_upload_first.setVisibility(View.VISIBLE);
            rl_upload_second.setVisibility(View.GONE);
            rl_upload_third.setVisibility(View.GONE);
            rl_upload_button.setVisibility(View.VISIBLE);
        } else if (shopFaceList.size() == 0) {
            rl_upload_first.setVisibility(View.GONE);
            rl_upload_second.setVisibility(View.GONE);
            rl_upload_third.setVisibility(View.GONE);
            rl_upload_button.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < shopFaceList.size(); i++) {
            if (i == 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(shopFaceList.get(0).getAbsolutePath());
                image_first.setImageBitmap(bitmap);
            } else if (i == 1) {
                Bitmap bitmap = BitmapFactory.decodeFile(shopFaceList.get(1).getAbsolutePath());
                image_second.setImageBitmap(bitmap);
            } else if (i == 2) {
                Bitmap bitmap = BitmapFactory.decodeFile(shopFaceList.get(2).getAbsolutePath());
                image_third.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied() {

    }

    private File yasuo2(String filePath) {
        // 200是将图片压缩到最大200KB大小
        return Utils.saveCompressBitmap(BitmapUtils.compressBitmap(filePath, 2), 200, filePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case UploadPopupWindows.REQ_CHOOSE_PHOTO_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData(); // 得到Uri
                    String filePath = FileUtil.getPath(this, uri);
                    /**
                     * 压缩图片
                     */
                    try {
                        File file = yasuo2(filePath);
                        shopFaceList.add(file);
                        showFace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case UploadPopupWindows.REQ_CHOOSE_CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    String Path = "";
                    if (uploadPopupWindows != null && uploadPopupWindows.getFilePath() != null) {
                        Path = uploadPopupWindows.getFilePath();
                    }
                    if (Utils.notEmpty(Path) && uploadPopupWindows.getUri() != null) {
                        try {
                            File file = yasuo2(Path);
                            shopFaceList.add(file);
                            showFace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
//                        MyToast.show("拍照失败，请重新拍照");
                    }
                }
                break;
            default:
                break;
        }
    }
}
