// IOnNewBookArrivedListener.aidl
package com.utte.aidltest;

import com.utte.aidltest.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
