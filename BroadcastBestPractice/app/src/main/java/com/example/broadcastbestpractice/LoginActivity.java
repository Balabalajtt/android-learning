package com.example.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button button;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        boolean flag = mPreferences.getBoolean("flag", false);

        if (flag) {
            accountEdit.setText(mPreferences.getString("account", ""));
            passwordEdit.setText(mPreferences.getString("password", ""));
            mCheckBox.setChecked(true);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("jiangtingting") && password.equals("zengshuaizhi")) {
                    mEditor = mPreferences.edit();
                    if (mCheckBox.isChecked()) {
                        mEditor.putBoolean("flag", true);
                        mEditor.putString("account", account);
                        mEditor.putString("password", password);
                    } else {
                        mEditor.clear();
                    }
                    mEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码有误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
