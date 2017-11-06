package com.example.mvptest.ipinfo;

import com.example.mvptest.model.IpInfo;
import com.example.mvptest.BaseView;

/**
 * Created by 江婷婷 on 2017/11/6.
 * presenter和view之间用接口交互
 */

public interface IpInfoContract {
    interface Presenter {
        void getIpInfo(String ip);
    }

    interface View extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);
        void showLoading();
        void hideLoading();
        void showError();
        boolean isActive();
    }
}
