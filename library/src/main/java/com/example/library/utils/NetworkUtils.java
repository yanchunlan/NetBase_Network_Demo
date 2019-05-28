package com.example.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.library.NetworkManager;
import com.example.library.type.NetType;


/**
 * author:  ycl
 * date:  2019/5/28 22:53
 * desc:
 */
public class NetworkUtils {

    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return false;
        }
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if (null != infos) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return NetType.NONE;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null == info) {
            return NetType.NONE;
        }
        int nType = info.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (info.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }


    public static void toSettings(Context context, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
