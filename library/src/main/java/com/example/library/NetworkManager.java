package com.example.library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.example.library.utils.Constants;


/**
 * author:  ycl
 * date:  2019/5/28 22:53
 * desc:
 */
public class NetworkManager {

    private static volatile NetworkManager sInstance = null;
    private Application mApplication;
    private NetworkStateReceiver mReceiver;
    private NetworkCallbackImpl mNetworkCallback;
    private ConnectivityManager mConnectivityManager;

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if (sInstance == null) {
            synchronized (NetworkManager.class) {
                if (sInstance == null) {
                    sInstance = new NetworkManager();
                }
            }
        }
        return sInstance;
    }


    public Application getApplication() {
        if (null == mApplication) {
            throw new RuntimeException("application == null");
        }
        return mApplication;
    }

    @SuppressLint("MissingPermission")
    public void init(Application application) {
        this.mApplication = application;
        if (onLollipop()) {
            mNetworkCallback = new NetworkCallbackImpl();
            mConnectivityManager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != mConnectivityManager) {
                NetworkRequest request = new NetworkRequest.Builder().build();
                mConnectivityManager.registerNetworkCallback(request, mNetworkCallback);
            }
        } else {
            mReceiver = new NetworkStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
            mApplication.registerReceiver(mReceiver, filter);
        }
    }


    public void registerObserver(Object object) {
        if (onLollipop()) {
            mNetworkCallback.registerObserver(object);
        } else {
            mReceiver.registerObserver(object);
        }
    }

    public void unRegisterObserver(Object object) {
        if (onLollipop()) {
            mNetworkCallback.unRegisterObserver(object);
        } else {
            mReceiver.unRegisterObserver(object);
        }
    }

    public void unRegisterAllObserver() {
        if (onLollipop()) {
            mNetworkCallback.unRegisterAllObserver();
            if (null != mConnectivityManager) {
                mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
            }
        } else {
            mReceiver.unRegisterAllObserver();
            mApplication.unregisterReceiver(mReceiver);
        }
    }

    public boolean onLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP; // > 5.0 true
    }

}
