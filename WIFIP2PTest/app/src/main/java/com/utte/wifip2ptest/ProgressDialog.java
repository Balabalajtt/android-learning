package com.utte.wifip2ptest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.utte.wifip2ptest.utils.ScreenUtils;

public class ProgressDialog extends AlertDialog.Builder {

    private TextView mProgressText;
    private ProgressBar mProgress;

    private AlertDialog mDialog;
    private View mView;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {

        mDialog = this.create();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.height = ScreenUtils.dip2px(getContext(), 130);
        params.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(params);

        mView = LayoutInflater.from(getContext()).inflate(R.layout.progressbar, null);
        mProgressText = mView.findViewById(R.id.tv_text);
        mProgress = mView.findViewById(R.id.pb_process);
        mDialog.show();
        mDialog.setContentView(mView);
        mDialog.setCancelable(false);
    }

    public void setProgressText(String text){
        mProgressText.setText(text);
    }

    public void setProgress(int progress){
        mProgress.setProgress(progress);
    }

    public void dismiss(){
        mDialog.dismiss();
    }

}
