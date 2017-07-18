package com.example.anlnsec;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/18.
 */

public class ListViewDemoActivity extends Activity {

    private ListView mPhoneBook;
    private List<UserInfo> mUserInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        mPhoneBook = (ListView) findViewById(R.id.list_view);

        mUserInfos = new ArrayList<>();
        mUserInfos.add(new UserInfo("jiangtingting"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        mUserInfos.add(new UserInfo("zengshuaizhi"));
        final PhoneBookAdapter phoneBookAdapter = new PhoneBookAdapter(ListViewDemoActivity.this, mUserInfos);



        mPhoneBook.setAdapter(phoneBookAdapter);
        mPhoneBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(i == 0)
                {
                    mUserInfos.clear();
                    mUserInfos.add(new UserInfo("我是江婷婷"));

                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));
                    mUserInfos.add(new UserInfo("zengshuaizhi"));

                    phoneBookAdapter.refreshData(mUserInfos);

                }

                if (mUserInfos.get(i).getUserName() == "zengshuaizhi") {
                    Toast.makeText(ListViewDemoActivity.this, mUserInfos.get(i).getUserName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListViewDemoActivity.this, mUserInfos.get(i).getUserName(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        mPhoneBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mUserInfos.get(i).getUserName() == "zengshuaizhi") {
                    Toast.makeText(ListViewDemoActivity.this, "我喜欢jiangtingting", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListViewDemoActivity.this,"我喜欢zengshuaizhi", Toast.LENGTH_SHORT).show();
                }
                return false;
            }


        });




    }




}
