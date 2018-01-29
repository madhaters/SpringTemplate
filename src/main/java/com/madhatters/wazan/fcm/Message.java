package com.madhatters.wazan.fcm;

import java.io.Serializable;
import java.util.HashMap;

abstract public class Message implements Serializable {
    private Notification notification;
    private HashMap<String, String> data;
    private String name;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
