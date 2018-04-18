package com.utte.wifip2ptest.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.utte.wifip2ptest.FileBean;
import com.utte.wifip2ptest.utils.FileUtils;
import com.utte.wifip2ptest.utils.Md5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收文件Socket
 */
public class ReceiveSocket {

    private static final String TAG = "ReceiveSocket";

    private static final int PORT = 10000;

    private ServerSocket mServerSocket;

    private InputStream mInputStream;

    private ObjectInputStream mObjectInputStream;

    private FileOutputStream mFileOutputStream;

    private File mFile;

    //ui线程的Handler
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 40:
                    if (mListener != null) {
                        mListener.onReceiveStart();
                    }
                    break;
                case 50:
                    int progress = (int) msg.obj;
                    if (mListener != null) {
                        mListener.onProgressChanged(mFile, progress);
                    }
                    break;
                case 60:
                    if (mListener != null) {
                        mListener.onFinished(mFile);
                    }
                    break;
                case 70:
                    if (mListener != null) {
                        mListener.onFailure(mFile);
                    }
                    break;
            }
        }
    };

    public void createServerSocket() {
        try {
            mServerSocket = new ServerSocket();
            mServerSocket.setReuseAddress(true);
            mServerSocket.bind(new InetSocketAddress(PORT));
            Socket socket = mServerSocket.accept();
            mInputStream = socket.getInputStream();
            mObjectInputStream = new ObjectInputStream(mInputStream);
            FileBean fileBean = (FileBean) mObjectInputStream.readObject();
            String name = new File(fileBean.filePath).getName();
            Log.e(TAG, "客户端IP地址 : " + socket.getRemoteSocketAddress());
            Log.e(TAG, "客户端传递的文件名称 : " + name);
            Log.e(TAG, "客户端传递的MD5 : " + fileBean.md5);
            mFile = new File(FileUtils.SdCardPath(name));
            mFileOutputStream = new FileOutputStream(mFile);
            //开始接收文件
            mHandler.sendEmptyMessage(40);
            byte bytes[] = new byte[1024];
            int len;
            long total = 0;
            int progress;
            while ((len = mInputStream.read(bytes)) != -1) {
                mFileOutputStream.write(bytes, 0, len);
                total += len;
                progress = (int) ((total * 100) / fileBean.fileLength);
                Log.e(TAG, "文件接收进度: " + progress);
                Message message = Message.obtain();
                message.what = 50;
                message.obj = progress;
                mHandler.sendMessage(message);
            }
            //新写入文件的MD5
            String md5New = Md5Util.getMd5(mFile);
            //发送过来的MD5
            String md5Old = fileBean.md5;
            if (md5New != null || md5Old != null) {
                if (md5New != null && md5New.equals(md5Old)) {
                    mHandler.sendEmptyMessage(60);
                    Log.e(TAG, "文件接收成功");
                }
            } else {
                mHandler.sendEmptyMessage(70);
            }

            mServerSocket.close();
            mInputStream.close();
            mObjectInputStream.close();
            mFileOutputStream.close();
        } catch (Exception e) {
            mHandler.sendEmptyMessage(70);
            Log.e(TAG, "文件接收异常" + e.getMessage());
        }
    }


    /**
     * 接受进度监听
     */
    private ProgressReceiveListener mListener;

    public void setOnProgressReceiveListener(ProgressReceiveListener listener) {
        mListener = listener;
    }

    public interface ProgressReceiveListener {
        //开始传输
        void onReceiveStart();

        //当传输进度发生变化时
        void onProgressChanged(File file, int progress);

        //当传输结束时
        void onFinished(File file);

        //传输失败回调
        void onFailure(File file);
    }


    public void clean() {
        try {
            if (mServerSocket != null) {
                mServerSocket.close();
            }
            if (mInputStream != null) {
                mInputStream.close();
            }
            if (mObjectInputStream != null) {
                mObjectInputStream.close();
            }
            if (mFileOutputStream != null) {
                mFileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}