package com.madhatters.wazan.fcm;

public class TopicMessage extends Message {
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
