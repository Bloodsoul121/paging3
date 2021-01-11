package com.blood.jetpackpaging3;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }
}
