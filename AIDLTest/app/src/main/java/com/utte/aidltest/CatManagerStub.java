package com.utte.aidltest;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by 江婷婷 on 2018/6/11.
 */

public class CatManagerStub extends Binder implements ICatManager {

    public CatManagerStub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static ICatManager asInterface(IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof ICatManager))) {
            return (ICatManager) iin;
        }
        return new CatManagerStub.Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                break;
            case TRANSACTION_getCatColor:
                data.enforceInterface(DESCRIPTOR);
                int color = this.getCatColor(data.readString());
                reply.writeNoException();
                reply.writeInt(color);
                return true;
            case TRANSACTION_getCatList:
                data.enforceInterface(DESCRIPTOR);
                List<Cat> result = this.getCatList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSACTION_addCat:
                data.enforceInterface(DESCRIPTOR);
                this.addCat(Cat.CREATOR.createFromParcel(data));
                reply.writeNoException();
                return true;
        }

        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public int getCatColor(String catName) throws RemoteException {
        return 0;
    }

    @Override
    public List<Cat> getCatList() throws RemoteException {
        return null;
    }

    @Override
    public void addCat(Cat cat) throws RemoteException {}

    private static class Proxy implements ICatManager {

        private IBinder mRemote;

        public Proxy(IBinder remote) {
            mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        @Override
        public int getCatColor(String catName) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            int color;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeString(catName);
                mRemote.transact(TRANSACTION_getCatColor, data, reply, 0);
                reply.readException();
                color = reply.readInt();
            } finally {
                data.recycle();
                reply.recycle();
            }
            return color;
        }

        @Override
        public List<Cat> getCatList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Cat> cats;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getCatList, data, reply, 0);
                reply.readException();
                cats = reply.createTypedArrayList(Cat.CREATOR);
            } finally {
                data.recycle();
                reply.recycle();
            }

            return cats;
        }

        @Override
        public void addCat(Cat cat) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                cat.writeToParcel(data, 0);
                mRemote.transact(TRANSACTION_addCat, data, reply, 0);
                reply.readException();
            } finally {
                data.recycle();
                reply.recycle();
            }
        }

    }

}
