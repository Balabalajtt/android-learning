package com.utte.aidltest.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.utte.aidltest.Book;
import com.utte.aidltest.IBookManager;
import com.utte.aidltest.IOnNewBookArrivedListener;
import com.utte.aidltest.R;
import com.utte.kkk.BookManagerService;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BookManagerActivity";

    IBookManager mBinder;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
//            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    };

    IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(final Book newBook) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onNewBookArrived: " + newBook.bookId + " " + newBook.bookName);
                }
            });

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinder == null) {
                return;
            }
            mBinder.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinder = null;
            Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        Button btGetBookList = findViewById(R.id.bt_get_book_list);
        Button btAddBook = findViewById(R.id.bt_add_book);
        Button btRegister = findViewById(R.id.bt_registe_listener);
        Button btUnregister = findViewById(R.id.bt_unregiste_listener);
        btGetBookList.setOnClickListener(this);
        btAddBook.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        btUnregister.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.bt_get_book_list:
                    List<Book> bookList = null;
                    bookList = mBinder.getBookList();
                    Log.d(TAG, "onClick: " + bookList.getClass().getCanonicalName());
                    for (Book book : bookList) {
                        Log.d(TAG, "onClick: " + book.bookId + " " + book.bookName);
                    }
                    break;
                case R.id.bt_add_book:
                    mBinder.addBook(new Book(0, "clientBook"));
                    break;
                case R.id.bt_registe_listener:
                    mBinder.registerListener(mListener);
                    break;
                case R.id.bt_unregiste_listener:
                    mBinder.unregisterListener(mListener);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}