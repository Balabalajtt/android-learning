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
        //互相注入
        //传入fragment给Presenter，接口的View继承的BaseView View--注入-->Presenter
        //传入ipInfoTask给Presenter Model--注入-->Presenter
        ipInfoPresenter = new IpInfoPresenter(ipInfoFragment, ipInfoTask);
        //(上一步注入好的Model)<--调用--Presenter--注入-->View
        ipInfoFragment.setPresenter(ipInfoPresenter);
    }
}
