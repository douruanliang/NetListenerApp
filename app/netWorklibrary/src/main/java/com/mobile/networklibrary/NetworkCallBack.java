package com.mobile.networklibrary;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 *
 * 方案二
 */

// 4.4+
public class NetworkCallBack  extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG, "网络已连接"+ network.toString());
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG, "网络丢失"+ network.toString());
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);

        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.e(Constants.LOG_TAG,"网络发生变更，当前网络为WIFI");
            }else {
                Log.e(Constants.LOG_TAG,"网络发生变更，当前网络为其他");
            }
        }
    }
}
