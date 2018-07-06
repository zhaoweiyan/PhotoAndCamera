package com.mygit.photoandcamera;

import android.app.Application;
import android.content.Context;


/**
 * Created by admin on 2015/11/3.
 */
public class MyApplication extends Application {

    private static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return sApplicationContext;
    }

}
