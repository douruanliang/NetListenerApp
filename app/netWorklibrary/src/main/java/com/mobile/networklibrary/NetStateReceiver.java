package com.mobile.networklibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 */
public class NetStateReceiver extends BroadcastReceiver {
    private NetType mNetType;
    private NetChangeObserver mNetChangeObserver;

    public NetStateReceiver() {
        mNetType = NetType.NONE;
    }

    public void setNetChangeObserver(NetChangeObserver netChangeObserver) {
        mNetChangeObserver = netChangeObserver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.LOG_TAG, "网络异常！");
        }

        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "网络发生了改变。");

            if (NetWorkUtil.isNetworkAvailable2()) {
                Log.e(Constants.LOG_TAG, "网络连接成功");
                mNetType = NetWorkUtil.getNetType();
                if (mNetChangeObserver != null) {
                    mNetChangeObserver.onConnect(mNetType);
                }
            } else {
                Log.e(Constants.LOG_TAG, "网络连接失败");
                if (mNetChangeObserver != null) {
                    mNetChangeObserver.onDisConnect();
                }
            }
        }
    }
}
