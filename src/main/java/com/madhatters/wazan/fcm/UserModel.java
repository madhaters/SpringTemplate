package com.madhatters.wazan.fcm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

public class UserModel implements Serializable {

    @Id
    private String id;
    @Indexed
    private String userId;

    @Indexed
    private String androidToken;

    @Indexed
    private String iosToken;

    public UserModel(String androidToken,
                     String iosToken, String userId, String id) {
        this.androidToken = androidToken;
        this.iosToken = iosToken;
        this.userId = userId;
        this.id = id;
    }


    public String getAndroidToken() {
        return androidToken;
    }

    public void setAndroidToken(String androidToken) {
        this.androidToken = androidToken;
    }

    public String getIosToken() {
        return iosToken;
    }

    public void setIosToken(String iosToken) {
        this.iosToken = iosToken;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
