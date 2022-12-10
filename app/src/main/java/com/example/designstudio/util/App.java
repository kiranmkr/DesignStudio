package com.example.designstudio.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

@SuppressLint("StaticFieldLeak")
public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.d("myApplication", "onCreate App");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
