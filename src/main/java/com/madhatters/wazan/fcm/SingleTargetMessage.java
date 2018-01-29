package com.madhatters.wazan.fcm;

public class SingleTargetMessage extends Message {
    private String token;

    public String getTarget() {
        return token;
    }

    public void setTarget(String target) {
        this.token = target;
    }
}
