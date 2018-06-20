package com.utte.aidltest.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utte.aidltest.Book;
import com.utte.aidltest.IBookManager;
import com.utte.aidltest.IOnNewBookArrivedListener;

import java.security.Permission;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 江婷婷 on 2018/6/13.
 */

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d(TAG, "onTransact: " + packageName);
            if (!packageName.startsWith("com.utte")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            onNewBookArrived(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new AddBookWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.utte.aidltest.aidl.ACCESS_BOOK_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            Log.d(TAG, "onBind: " + "no permission");
//            return null;
//        }
        return mBinder;
    }

    private void onNewBookArrived(Book book) {
        mBookList.add(book);
        int size = mListenerList.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    /**
     * 自动每五秒添加一本书的线程
     */
    private class AddBookWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(mBookList.size() + 1, "ServiceBook");
                onNewBookArrived(newBook);
            }
        }
    }



}