package com.utte.oopsixprinciple.lkp;

import android.util.Log;

import java.util.List;

/**
 * Created by 江婷婷 on 2018/6/6.
 */

public class Tenant {
    private static final String TAG = "Tenant";
    public void rentRoom(float area, float price, Mediator mediator) {
        Log.d(TAG, "rentRoom: " + mediator.rentOut(area, price).toString());
    }

}
