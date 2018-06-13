// IBookManager.aidl
package com.utte.aidltest;

import com.utte.aidltest.Book;
import com.utte.aidltest.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);

}
