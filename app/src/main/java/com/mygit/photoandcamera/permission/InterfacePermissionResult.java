package com.mygit.photoandcamera.permission;

/**
 * Created by w on 2016/10/8.
 */
public interface InterfacePermissionResult {

    void onGranted();//允许

    void onDenied();  //拒绝

//    void onNotShowRationale();  //     不再提示申请权限
//
//    void onCancelShowRationale();  //取消申请权限
//
//    void onDeniedDialogPositive();  //取消权限后进入
//
//    void onDeniedDialogNegative();  //取消权限后退出

}
