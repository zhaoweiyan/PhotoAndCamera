package com.mygit.photoandcamera.utils;

import android.os.Environment;

/**
 * Created by admin on 2015/12/14.
 */
public class Constants {

    public static final String WECHAT_APP_ID = "wx3f1a40771c4654fc";
    public static final int IMAGE_NOT_NULL = 1;
    public static final int IMAGE_NULL = 2;//分享地址为空

    public static final String SHARE_URL = "http://m.piaojiazi.com/app.html";

    //gif存储文件夹及路径
    public static String gifPath = Environment.getExternalStorageDirectory().getPath() + "/logo"; // apk保存到SD卡的路径
    public static String gifFileName = gifPath + "/start.gif"; // 完整路径名
}
