package com.madhatters.wazan.fcm;

public class ConditionalMessage extends Message {
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
