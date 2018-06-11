// IBookManager.aidl
package com.utte.aidltest;

import com.utte.aidltest.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
