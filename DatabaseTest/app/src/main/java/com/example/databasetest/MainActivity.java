package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDataBaseHelper mMyDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyDataBaseHelper = new MyDataBaseHelper(this, "BookStore.db", null, 1);

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
        SQLiteDatabase sqLiteDatabase;
        ContentValues contentValues;
        switch (view.getId()) {
            case R.id.create_button:
                mMyDataBaseHelper.getWritableDatabase();
                break;
            case R.id.add_button:
                sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
                contentValues = new ContentValues();
                //组装数据
                contentValues.put("name", "一只猪的美好生活");
                contentValues.put("author", "曾帅智");
                contentValues.put("pages", 322);
                contentValues.put("price", 20.2);
                sqLiteDatabase.insert("Book", null, contentValues);//插入数据
                contentValues.clear();
                contentValues.put("name", "仙女的美好生活");
                contentValues.put("author", "江婷婷");
                contentValues.put("pages", 222);
                contentValues.put("price", 20.2);
                sqLiteDatabase.insert("Book", null, contentValues);
                break;
            case R.id.update_button:
                sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
                contentValues = new ContentValues();
                contentValues.put("price", 29.99);
                sqLiteDatabase.update("Book", contentValues, "name=?", new String[] {"一只猪的美好生活"});
                break;
            case R.id.delete_button:
                sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
                sqLiteDatabase.delete("Book", "pages > ?", new String[] {"300"});
                break;
            case R.id.query_button:
                sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query("Book", null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "name:" + name);
                        Log.d("MainActivity", "author:" + author);
                        Log.d("MainActivity", "pages:" + pages);
                        Log.d("MainActivity", "price:" + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
        }
    }
}
