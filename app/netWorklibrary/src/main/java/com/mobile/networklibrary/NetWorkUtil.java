package com.mobile.networklibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 */
public class NetWorkUtil {

    /**
     * 获取ConnectivityManager.
     *
     * @return 上下文信息中获取的ConnectivityManager
     */
    private static ConnectivityManager getConnectivityManager() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }


    /**
     * 网络是否可用(新修改)
     *
     * @return
     */
    public static boolean isNetworkAvailable2() {
        ConnectivityManager connectivityManager = getConnectivityManager();
        NetworkInfo[] networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getAllNetworkInfo();
        }
        if (networkInfo != null) {
            for (NetworkInfo item : networkInfo) {
                if (item.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 网络状态 粗力度
     *
     * @return
     */
    public static NetType getNetType() {
        ConnectivityManager connectivityManager = getConnectivityManager();
        if (connectivityManager == null) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null) {
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                    return NetType.CMNET;
                } else {
                    return NetType.CMWAP;
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                return NetType.WIFI;
            }
        }
        return NetType.NONE;
    }

    /**
     * 获取活动的NetworkInfo.
     *
     * @param context Context
     * @return Active networkInfo
     */
    public static NetworkInfo getActiveNetworkInfo(final Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager();
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo;
    }


    /**
     * 检测网络是否可用.
     *
     * @param context Context
     * @return ture -- 网络可用, false -- 网络不可用
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) {
            throw new NullPointerException("context不能为空");
        }
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    /**
     * 获取用户的网络状态
     *
     * @return
     */
    public static String getDeviceNetworkStatus(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }
}
