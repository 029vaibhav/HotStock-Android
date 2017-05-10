package com.vaibhav.hotstock;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by vaibhav on 10/5/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

