package com.mobile.netlistenerapp;

import android.os.Bundle;
import android.util.Log;

import com.mobile.networklibrary.Constants;
import com.mobile.networklibrary.NetChangeObserver;
import com.mobile.networklibrary.NetType;
import com.mobile.networklibrary.NetworkManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().setListener(this);
    }

    @Override
    public void onDisConnect() {
        Log.e(Constants.LOG_TAG, "none net word");
    }

    @Override
    public void onConnect(NetType type) {
        Log.e(Constants.LOG_TAG, "type -" + type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unregisterListener();
    }
}