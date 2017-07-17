package com.example.anln;

import java.io.Serializable;

/**
 * Created by 江婷婷 on 2017/7/17.
 */

public class UserInfo implements Serializable{
    private String mUserName;
    private int mAge;
    public UserInfo(String name,int age)
    {
        mUserName = name;
        mAge = age;
    }

    public void setmUserName(String name)
    {
        mUserName = name;
    }
    public String getmUserName()
    {
        return mUserName;
    }
    public void setmAge(int age)
    {
        mAge = age;
    }
    public int getmAge()
    {
        return mAge;
    }

}

