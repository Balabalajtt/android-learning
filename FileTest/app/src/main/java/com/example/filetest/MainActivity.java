package com.example.filetest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button loadButton = (Button) findViewById(R.id.load_button);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        String text;
        switch (view.getId()) {

            case R.id.save_button:
                text = mEditText.getText().toString();
                save(text);
                break;

            case R.id.load_button:
                text = load();
                mEditText.setText(text);
                mEditText.setSelection(text.length());
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save (String text) {
        try (
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("data", Context.MODE_PRIVATE)))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        showToast("save successfully");
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String load() {
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("data")))) {
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        showToast("load successfully");
        return stringBuilder.toString();
    }

    public void showToast (String string) {
        if(mToast==null)
        {
            mToast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
            mToast.show();
        }
        else
        {
            mToast.setText(string);
            mToast.show();
        }
    }
}
