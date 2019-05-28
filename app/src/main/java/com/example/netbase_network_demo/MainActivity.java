package com.example.netbase_network_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.library.NetworkManager;
import com.example.library.annotation.Network;
import com.example.library.type.NetType;
import com.example.library.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private TextView tv_network1;
    private TextView tv_network2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        NetworkManager.getInstance().registerObserver(this);
    }

    private void initView() {
        tv_network1 = (TextView) findViewById(R.id.tv_network1);
        tv_network2 = (TextView) findViewById(R.id.tv_network2);
    }


    // only monitor wifi
    @Network(netType = NetType.WIFI)
    public void network1(NetType netType) {
        toLog(netType, "network1");
        tv_network1.setText(netType.toString());
    }

    // monitor all
    @Network(netType = NetType.AUTO)
    public void network2(NetType netType) {
        toLog(netType, "network2");
        tv_network2.setText(netType.toString());
    }

    private void toLog(NetType netType, String tag) {
        switch (netType) {
            case AUTO:
                Log.d(Constants.LOG_TAG, tag + " -- AUTO ");
                break;
            case WIFI:
                Log.d(Constants.LOG_TAG, tag + " -- WIFI ");
                break;
            case CMNET:
                Log.d(Constants.LOG_TAG, tag + " -- CMNET ");
                break;
            case CMWAP:
                Log.d(Constants.LOG_TAG, tag + " -- CMWAP ");
                break;
            case NONE:
                Log.d(Constants.LOG_TAG, tag + " -- NONE ");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        NetworkManager.getInstance().unRegisterObserver(this);
        NetworkManager.getInstance().unRegisterAllObserver();
    }
}
