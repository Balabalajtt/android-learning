package com.utte.wifip2ptest.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.utte.wifip2ptest.FileBean;
import com.utte.wifip2ptest.ProgressDialog;
import com.utte.wifip2ptest.socket.SendSocket;

import java.io.File;

/**
 * 客户端发送文件的Task
 */

public class SendTask extends AsyncTask<String, Integer, Void> implements SendSocket.ProgressSendListener {

    private static final String TAG = "SendTask";

    private FileBean mFileBean;
    private Context mContext;
    private SendSocket mSendSocket;
    private ProgressDialog mProgressDialog;


    public SendTask(Context ctx, FileBean fileBean) {
        mFileBean = fileBean;
        mContext = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //创建进度条
        mProgressDialog = new ProgressDialog(mContext);
    }

    @Override
    protected Void doInBackground(String... strings) {
        mSendSocket = new SendSocket(mFileBean, strings[0], this);
        mSendSocket.createSendSocket();
        return null;
    }


    @Override
    public void onProgressChanged(File file, int progress) {
        Log.e(TAG, "发送进度：" + progress);
        mProgressDialog.setProgress(progress);
        mProgressDialog.setProgressText(progress + "%");
    }

    @Override
    public void onFinished(File file) {
        Log.e(TAG, "发送完成");
        mProgressDialog.dismiss();
        Toast.makeText(mContext, file.getName() + "发送完毕！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(File file) {
        Log.e(TAG, "发送失败");
        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
        Toast.makeText(mContext, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
    }
}
