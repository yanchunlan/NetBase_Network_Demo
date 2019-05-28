package com.example.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.library.bean.NetBean;
import com.example.library.type.NetType;
import com.example.library.utils.Constants;
import com.example.library.utils.NetAnnotationUtils;
import com.example.library.utils.NetworkUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  ycl
 * date:  2019/5/28 22:53
 * desc:
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private NetType mNetType;
    private Map<Object, List<NetBean>> mNetworkList;

    public NetworkStateReceiver() {
        mNetType = NetType.NONE;
        mNetworkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.d(Constants.LOG_TAG, "NetworkStateReceiver - network change");
            mNetType = NetworkUtils.getNetType();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.d(Constants.LOG_TAG, "NetworkStateReceiver - network connect");
            } else {
                Log.d(Constants.LOG_TAG, "NetworkStateReceiver - network disconnect");
            }
            NetAnnotationUtils.post(mNetType, mNetworkList);
        }
    }

    public void registerObserver(Object object) {
        List<NetBean> methodList = mNetworkList.get(object);
        if (methodList == null) {
            methodList = NetAnnotationUtils.findAnnotationMethod(object);
            mNetworkList.put(object, methodList);
        }
    }

    public void unRegisterObserver(Object object) {
        if (!mNetworkList.isEmpty()) {
            mNetworkList.remove(object);
        }
    }

    public void unRegisterAllObserver() {
        if (!mNetworkList.isEmpty()) {
            mNetworkList.clear();
        }
    }

}
