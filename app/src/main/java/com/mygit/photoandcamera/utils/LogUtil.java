package com.mygit.photoandcamera.utils;

import android.util.Log;

/**
 * Created by admin on 2017/2/27.
 */

public class LogUtil {
    public static boolean isOpen = true;

    public static void i(String tag) {
        if (isOpen) {
            Log.i("TAG", "info日志tag------" + tag);
        }
    }

    public static void e(String tag) {
        if (isOpen) {
            Log.e("TAG", "info日志tag------" + tag);
        }
    }
}
