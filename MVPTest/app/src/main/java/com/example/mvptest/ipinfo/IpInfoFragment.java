package com.example.mvptest.ipinfo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvptest.model.IpData;
import com.example.mvptest.model.IpInfo;
import com.example.mvptest.R;

/**
 * Created by 江婷婷 on 2017/11/6.
 * 实现View接口
 */

public class IpInfoFragment extends Fragment implements IpInfoContract.View {

    private TextView tv_country;
    private TextView tv_area;
    private TextView tv_city;
    private Button bt_ipinfo;
    private Dialog mDialog;
    private IpInfoContract.Presenter mPresenter;
    public static IpInfoFragment newInstance() {
        return new IpInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ipinfo, container, false);
        tv_country = root.findViewById(R.id.tv_country);
        tv_area = root.findViewById(R.id.t_area);
        tv_city = root.findViewById(R.id.tv_city);
        bt_ipinfo = root.findViewById(R.id.bt_ipinfo);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("正在获取数据");
        bt_ipinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getIpInfo("117.136.86.53");
            }
        });
    }

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null && ipInfo.getData() != null) {
            IpData ipData = ipInfo.getData();
            tv_country.setText(ipData.getCountry());
            tv_area.setText(ipData.getArea());
            tv_city.setText(ipData.getCity());
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity().getApplicationContext(), "网络出错",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
