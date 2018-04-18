package com.utte.wifip2ptest.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.utte.wifip2ptest.FileBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端发送的socket
 */

public class SendSocket {

    public static final String TAG = "SendSocket";
    public static final int PORT = 10000;
    private FileBean mFileBean;
    private String mAddress;
    private File mFile;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    int progress = (int) msg.obj;
                    if (mListener != null) {
                        mListener.onProgressChanged(mFile, progress);
                    }
                    break;
                case 20:
                    if (mListener != null) {
                        mListener.onFinished(mFile);
                    }
                    break;
                case 30:
                    if (mListener != null) {
                        mListener.onFailure(mFile);
                    }
                    break;
            }
        }
    };

    public SendSocket(FileBean fileBean, String address, ProgressSendListener listener) {
        mFileBean = fileBean;
        mAddress = address;
        mListener = listener;
    }

    public void createSendSocket() {
        try {
            Socket socket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(mAddress, PORT);
            Log.e("1111", "createSendSocket:" + inetSocketAddress);
            socket.connect(inetSocketAddress);
            Log.e("1111", "createSendSocket:");
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mFileBean);
            mFile = new File(mFileBean.filePath);
            FileInputStream inputStream = new FileInputStream(mFile);
            long size = mFileBean.fileLength;
            long total = 0;
            byte bytes[] = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                total += len;
                int progress = (int) ((total * 100) / size);
                Log.e(TAG, "文件发送进度：" + progress);
                Message message = Message.obtain();
                message.what = 10;
                message.obj = progress;
                mHandler.sendMessage(message);
            }
            outputStream.close();
            objectOutputStream.close();
            inputStream.close();
            socket.close();
            mHandler.sendEmptyMessage(20);
            Log.e(TAG, "文件发送成功");
        } catch (Exception e) {
            mHandler.sendEmptyMessage(30);
            Log.e(TAG, "文件发送异常");
            e.printStackTrace();
        }
    }

    /**
     * 监听发送进度
     */
    private ProgressSendListener mListener;

    public interface ProgressSendListener {

        //当传输进度发生变化时
        void onProgressChanged(File file, int progress);

        //当传输结束时
        void onFinished(File file);

        //传输失败时
        void onFailure(File file);
    }
}

