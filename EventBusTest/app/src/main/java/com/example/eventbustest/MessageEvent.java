package com.example.eventbustest;

/**
 * Created by 江婷婷 on 2017/10/14.
 */

//定义消息类
public class MessageEvent {

    private  String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
