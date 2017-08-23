package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.add_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        addData.setOnClickListener(this);
        queryData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Uri uri;
        ContentValues values;
        Cursor cursor;
        switch (view.getId()) {
            case R.id.add_data:
                uri = Uri.parse("content://com.example.databasetest.provider/book");
                values = new ContentValues();
                values.put("name", "猪和仙女的美好生活");
                values.put("author", "jtt");
                values.put("pages", 500);
                values.put("price", 66.6);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                break;
            case R.id.query_data:
                uri = Uri.parse("content://com.example.databasetest.provider/book");
                cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "" + name + author + pages + price);
                    }
                    cursor.close();
                }
                break;
            case R.id.update_data:
                uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                values = new ContentValues();
                values.put("price", 77.9);
                getContentResolver().update(uri, values, null, null);
                break;
            case R.id.delete_data:
                uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri, null, null);
                break;
        }
    }
}
