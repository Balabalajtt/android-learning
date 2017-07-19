package com.example.anlnsec;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/18.
 */

public class GridViewDemoActivity extends Activity {

    private GridView mGridView;
    private List<UserInfo> mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_demo);

        mGridView = (GridView)findViewById(R.id.grid_view);

        mUser = new ArrayList<>();
        mUser.add(new UserInfo("jtt"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
        mUser.add(new UserInfo("zsz"));
       final GridAdapter gridAdapter = new GridAdapter(GridViewDemoActivity.this, mUser);


        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(i == 0)
                {
                    mUser.clear();
                    mUser.add(new UserInfo("JTT"));

                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));
                    mUser.add(new UserInfo("zsz"));

                    gridAdapter.refreshData(mUser);

                }

                Toast.makeText(GridViewDemoActivity.this, mUser.get(i).getUserName(), Toast.LENGTH_SHORT).show();

            }
        });


        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mUser.get(i).getUserName() == "zsz") {
                    Toast.makeText(GridViewDemoActivity.this, "我喜欢jiangtingting", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GridViewDemoActivity.this,"我喜欢zengshuaizhi", Toast.LENGTH_SHORT).show();
                }
                return false;
            }


        });


    }


}
