package com.mygit.photoandcamera.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mygit.photoandcamera.R;


/**
 * Created by w on 2016/10/8.
 */
public class PermissionUtils {

    private Context context;
    private Activity activity;
    private View applyDialogBuilderView, deniedDialogBuilderView;
    private TextView applyDialogBuilderViewMessage, deniedDialogBuilderViewMessage;
    private TextView applyDialogPositiveButtonText, applyDialogNegativeButtonText;
    private TextView deniedDialogPositiveButtonText, deniedDialogNegativeButtonText;
    private AlertDialog.Builder applyDialogBuilder, deniedDialogBuilder;
    private AlertDialog applyDialog, deniedDialog;

    private int REQUEST_CODE;
    private String[] PERMISSIONS;
    private InterfacePermissionResult interfacePermissionResult;

    public PermissionUtils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        initDialog();
    }

    private void initApplyDialog() {
        //申请权限弹窗
        applyDialogBuilder = new AlertDialog.Builder(activity);
        applyDialogBuilderView = LayoutInflater.from(context).inflate(R.layout.pop_textdialog, null);
        applyDialogBuilderView.findViewById(R.id.tv_pop_title).setVisibility(View.GONE);
        applyDialogBuilderViewMessage = (TextView) applyDialogBuilderView.findViewById(R.id.tv_pop_content);
        applyDialogNegativeButtonText = (TextView) applyDialogBuilderView.findViewById(R.id.tv_textdialogcancel);
        applyDialogPositiveButtonText = (TextView) applyDialogBuilderView.findViewById(R.id.tv_textdialogconfirm);
        applyDialogBuilder.setView(applyDialogBuilderView);
        applyDialogBuilder.setCancelable(false);

        applyDialogPositiveButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDialog.dismiss();
                requestPermission();
            }
        });

        applyDialogNegativeButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDialog.dismiss();
//                if (interfacePermissionResult != null)
//                    interfacePermissionResult.onCancelShowRationale();
            }
        });
        applyDialog = applyDialogBuilder.create();
        applyDialog.getWindow().setBackgroundDrawableResource(R.color.colortrans);
    }

    private void initDeniedDialog() {
        //申请权限弹窗
        deniedDialogBuilder = new AlertDialog.Builder(activity);
        deniedDialogBuilderView = LayoutInflater.from(context).inflate(R.layout.pop_textdialog, null);
        deniedDialogBuilderView.findViewById(R.id.tv_pop_title).setVisibility(View.GONE);
        deniedDialogBuilderViewMessage = (TextView) deniedDialogBuilderView.findViewById(R.id.tv_pop_content);
        deniedDialogNegativeButtonText = (TextView) deniedDialogBuilderView.findViewById(R.id.tv_textdialogcancel);
        deniedDialogPositiveButtonText = (TextView) deniedDialogBuilderView.findViewById(R.id.tv_textdialogconfirm);
        deniedDialogBuilder.setView(deniedDialogBuilderView);
        deniedDialogBuilder.setCancelable(false);

        deniedDialogPositiveButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deniedDialog.dismiss();
                try {
                    context.startActivity(getAppDetailSettingIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (interfacePermissionResult != null)
//                    interfacePermissionResult.onDeniedDialogPositive();
            }
        });

        deniedDialogNegativeButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deniedDialog.dismiss();
//                if (interfacePermissionResult != null)
//                    interfacePermissionResult.onDeniedDialogNegative();
            }
        });
        deniedDialog = deniedDialogBuilder.create();
        deniedDialog.getWindow().setBackgroundDrawableResource(R.color.colortrans);
    }

    /**
     * 初始化弹窗
     */
    private void initDialog() {
        if (activity == null)
            return;
        //申请权限弹窗
        initApplyDialog();
        //权限禁止，提示弹窗
        initDeniedDialog();
    }

    /**
     * 设置接口回调
     *
     * @param interfacePermissionResult
     */
    public void setInterfacePermissionResult(InterfacePermissionResult interfacePermissionResult) {
        this.interfacePermissionResult = interfacePermissionResult;
    }

    /**
     * 检查权限
     *
     * @param permission
     * @param requestCode
     */
    public void checkPermission(String permission, int requestCode) {
        REQUEST_CODE = requestCode;
        PERMISSIONS = new String[]{permission};
        int checkPermission = ContextCompat.checkSelfPermission(context, permission);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {//权限不允许
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                if (interfacePermissionResult != null)
//                    interfacePermissionResult.onNotShowRationale();
            } else {
                requestPermission();
            }
        } else {
            if (interfacePermissionResult != null)
                interfacePermissionResult.onGranted();
        }
    }


    /**
     * 请求权限
     */
    public void requestPermission() {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_CODE);
    }

    /**
     * 权限结果处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (PERMISSIONS.length == 1) {
                if (permissions[0].equals(PERMISSIONS[0]) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户允许此权限
                    if (interfacePermissionResult != null)
                        interfacePermissionResult.onGranted();
                } else {
                    if (interfacePermissionResult != null)
                        interfacePermissionResult.onDenied();

                }
            }
        }
    }

    /**
     * 展示申请权限弹窗
     *
     * @param message
     */
    public void showApplyDialog(String message) {
        showApplyDialog("", message);
    }

    /**
     * 展示申请权限弹窗
     *
     * @param title
     * @param message
     */
    public void showApplyDialog(String title, String message) {
        if (activity == null)
            return;
//        if (!TextUtils.isEmpty(title))
//            applyDialogBuilder.setTitle(title);
//        applyDialogBuilder.setMessage(message);
        applyDialogBuilderViewMessage.setText(message);
        applyDialog.show();
    }

    /**
     * 展示权限禁止信息说明弹窗
     *
     * @param message
     */
    public void showDeniedDialog(String message) {
        showDeniedDialog("", message);
    }

    /**
     * 展示权限禁止信息说明弹窗
     *
     * @param title
     * @param message
     */
    public void showDeniedDialog(String title, String message) {
        if (activity == null)
            return;
//        if (!TextUtils.isEmpty(title))
//            deniedDialogBuilder.setTitle(title);
//        deniedDialogBuilder.setMessage(message);
        deniedDialogBuilderViewMessage.setText(message);
        if(deniedDialog.isShowing()){
            deniedDialog.dismiss();
        }
        deniedDialog.show();
    }

    /**
     * 跳转应用信息设置界面
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
