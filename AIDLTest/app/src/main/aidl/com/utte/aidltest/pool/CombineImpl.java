package com.utte.aidltest.pool;

import android.os.RemoteException;

public class CombineImpl extends ICombine.Stub {
    @Override
    public String combine(String strA, String strB) throws RemoteException {
        return new String(strA + strB);
    }
}
