package com.utte.wifip2ptest.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.utte.wifip2ptest.WifiP2pActionListener;
import com.utte.wifip2ptest.WifiP2pReceiver;

import java.util.Collection;

/**
 * 初始WifiP2p
 * 注册广播
 * 默认实现WifiP2pActionListener接口
 */
public class BaseActivity extends AppCompatActivity implements WifiP2pActionListener {

    private static final String TAG = "BaseActivity";

    public WifiP2pManager mWifiP2pManager;

    public WifiP2pManager.Channel mChannel;

    public WifiP2pReceiver mWifiP2pReceiver;

    public WifiP2pInfo mWifiP2pInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化WifiP2pManager
        mWifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), this);

        //注册广播
        mWifiP2pReceiver = new WifiP2pReceiver(mWifiP2pManager, mChannel, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        registerReceiver(mWifiP2pReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mWifiP2pReceiver);
        mWifiP2pReceiver = null;
    }


    //实现监听接口
    @Override
    public void wifiP2pEnabled(boolean enabled) {
        Log.e(TAG, "wifiP2pEnabled: " + enabled);
    }

    @Override
    public void onConnection(WifiP2pInfo wifiP2pInfo) {
        Log.e(TAG, "onConnection: " + wifiP2pInfo.isGroupOwner +
                wifiP2pInfo.groupFormed + wifiP2pInfo.groupOwnerAddress);
        mWifiP2pInfo = wifiP2pInfo;
    }

    @Override
    public void onDisconnection() {
        Log.e(TAG, "onDisconnection: ");
    }

    @Override
    public void onDeviceInfo(WifiP2pDevice wifiP2pDevice) {
        Log.e(TAG, "onDeviceInfo: " + wifiP2pDevice.deviceName);
    }

    @Override
    public void onPeersInfo(Collection<WifiP2pDevice> wifiP2pDeviceList) {
        for (WifiP2pDevice device : wifiP2pDeviceList) {
            Log.e(TAG, "onPeersInfo: " + device.deviceName + " " + device.deviceAddress);
        }
    }

    @Override
    public void onChannelDisconnected() {
        Log.e(TAG, "onChannelDisconnected: ");
    }
}
