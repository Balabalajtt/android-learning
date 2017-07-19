package com.example.anlnsec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mListViewButton;
    private Button mGridViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews()
    {
        mListViewButton = (Button) findViewById(R.id.list_view_button);
        mGridViewButton = (Button) findViewById(R.id.grid_view_button);

        mListViewButton.setOnClickListener(this);
        mGridViewButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.list_view_button:
                startActivity(new Intent(MainActivity.this, ListViewDemoActivity.class));
                break;
            case R.id.grid_view_button:
                startActivity(new Intent(MainActivity.this, GridViewDemoActivity.class));
                break;
        }
    }


}
