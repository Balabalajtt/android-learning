package com.example.anlnsec;

import android.widget.ImageView;

/**
 * Created by 江婷婷 on 2017/7/18.
 */

public class UserInfo {
    private String UserName;
    private String mAvatarUrl;
    public UserInfo(String name)
    {
        UserName = name;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
}
