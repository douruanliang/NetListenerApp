package com.mobile.networklibrary;

/**
 * @author: douruanliang
 * @date: 2020/8/24
 */
public interface NetChangeObserver {
    void onDisConnect();
    void onConnect(NetType type);
}
