package com.mobile.netlistenerapp;

import android.app.Application;

import com.mobile.networklibrary.NetworkManager;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}
