package com.example.library;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.library.bean.NetBean;
import com.example.library.type.NetType;
import com.example.library.utils.Constants;
import com.example.library.utils.NetAnnotationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  ycl
 * date:  2019/5/28 23:05
 * desc:
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback{

    private NetType mNetType;
    private Map<Object, List<NetBean>> mNetworkList;

    public NetworkCallbackImpl() {
        mNetType = NetType.NONE;
        mNetworkList = new HashMap<>();
    }


    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(Constants.LOG_TAG, "NetworkCallbackImpl - onAvailable");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.d(Constants.LOG_TAG, "NetworkCallbackImpl - onLost");

        //onLost时不会触发onCapabilitiesChanged，所以这里单独推送
        NetAnnotationUtils.post(NetType.NONE, mNetworkList);
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.d(Constants.LOG_TAG, "NetworkCallbackImpl - onCapabilitiesChanged");

        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d(Constants.LOG_TAG, "NetworkCallbackImpl - wifi");
                //推送
                NetAnnotationUtils.post(NetType.WIFI, mNetworkList);
            } else {
                Log.d(Constants.LOG_TAG, "NetworkCallbackImpl - not wifi");
                //推送，这里先用cmwap表示非WiFi
                NetAnnotationUtils.post(NetType.CMWAP, mNetworkList);
            }
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
