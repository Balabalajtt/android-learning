package com.utte.aidltest;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

import static android.os.IBinder.FIRST_CALL_TRANSACTION;

/**
 * Created by 江婷婷 on 2018/6/11.
 */

public interface ICatManager extends IInterface {
    String DESCRIPTOR = "com.utte.aidltest.IBookManager";

    int TRANSACTION_getCatColor = FIRST_CALL_TRANSACTION;
    int TRANSACTION_getCatList = FIRST_CALL_TRANSACTION + 1;
    int TRANSACTION_addCat = FIRST_CALL_TRANSACTION + 2;

    int getCatColor(String catName) throws RemoteException;
    List<Cat> getCatList() throws RemoteException;
    void addCat(Cat cat) throws RemoteException;

}
