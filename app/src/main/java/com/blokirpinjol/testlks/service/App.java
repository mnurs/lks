package com.blokirpinjol.testlks.service;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        App.mContext = getApplicationContext();
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        App.mContext = mContext;
    }
}
