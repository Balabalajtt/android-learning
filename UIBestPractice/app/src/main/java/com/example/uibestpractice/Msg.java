package com.example.uibestpractice;

/**
 * Created by 江婷婷 on 2017/7/23.
 * Msg实体类 用作适配器类型 提供get方法
 */


public class Msg {

    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public Msg(String content, int type)
    {
        this.content = content;
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }

}
