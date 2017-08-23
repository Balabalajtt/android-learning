package com.example.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createButton = (Button) findViewById(R.id.create_button);
        Button addButton = (Button) findViewById(R.id.add_button);
        Button updateButton = (Button) findViewById(R.id.update_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        Button queryButton = (Button) findViewById(R.id.query_button);
        createButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        queryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Book book;
        switch (view.getId()) {
            case R.id.create_button:
                Connector.getDatabase();
                break;
            case R.id.add_button:
                book = new Book();
                book.setName("一只猪的美好生活");
                book.setAuthor("ZSZ");
                book.setPages(222);
                book.setPrice(20.2);
                book.save();
                break;
            case R.id.update_button:
                book = new Book();
                book.setPrice(19.9);
                book.setPages(266);
                //book.setToDefault("pages");//设置为默认值
                book.updateAll("name = ? and author = ?", "一只猪的美好生活", "ZSZ");
                break;
            case R.id.delete_button:
                DataSupport.deleteAll(Book.class, "price < ?", "20");//不指定约束条件时删除所有
                break;
            case R.id.query_button:
                List<Book> books = DataSupport.findAll(Book.class);
                for(Book b : books) {
                    Log.d("MainActivity", b.getName());
                    Log.d("MainActivity", b.getAuthor());
                    Log.d("MainActivity", b.getPages()+"");
                    Log.d("MainActivity", b.getPrice()+"");
                }
                break;
        }
    }
}
