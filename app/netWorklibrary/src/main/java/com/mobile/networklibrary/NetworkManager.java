package com.mobile.networklibrary;

import android.app.Application;
import android.content.IntentFilter;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 */
public class NetworkManager {

    private static volatile NetworkManager mInstance;
    private Application mApplication;
    private NetStateReceiver mNetStateReceiver;

    private NetworkManager(){
        mNetStateReceiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault(){
        if (mInstance == null){
            synchronized (NetworkManager.class){
                if (mInstance == null){
                    mInstance = new NetworkManager();
                }
            }
        }
        return mInstance;
    }

    public Application getApplication(){
        if (mApplication == null){
            throw new RuntimeException("mApplication == null");
        }
        return mApplication;
    }

    public void init(Application application){
        this.mApplication = application;
        //动态的广播注册 （7.0 +）
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);

        application.registerReceiver(mNetStateReceiver,filter);
    }

    /**
     * 供外面的API
     * @param listener
     */
    public void setListener(NetChangeObserver listener){
        if (listener!=null){
            mNetStateReceiver.setNetChangeObserver(listener);
        }
    }

    /**
     * 注销广播
     */
    public void unregisterListener(){
        if (mApplication!=null){
            mApplication.unregisterReceiver(mNetStateReceiver);
        }
    }
}
