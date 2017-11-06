package com.example.mvptest.ipinfo;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvptest.net.IpInfoTask;
import com.example.mvptest.R;
import com.example.mvptest.util.ActivityUtils;

public class IpInfoActivity extends AppCompatActivity {

    private IpInfoPresenter ipInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinfo);
        IpInfoFragment ipInfoFragment = (IpInfoFragment) getSupportFragmentManager().
                findFragmentById(R.id.fm_content);
        if (ipInfoFragment == null) {
            ipInfoFragment = IpInfoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ipInfoFragment, R.id.fm_content);
        }
        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        ipInfoPresenter = new IpInfoPresenter(ipInfoFragment, ipInfoTask);
        ipInfoFragment.setPresenter(ipInfoPresenter);
    }
}
