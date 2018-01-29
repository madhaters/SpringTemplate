package com.madhatters.wazan.fcm;


import com.google.gson.Gson;

import javax.validation.constraints.NotNull;

public final class MessageBuilder {
    private Notification notification;

    public MessageBuilder() {
        notification = new Notification();

    }

    public MessageBuilder title(String title) {
        notification.setTitle(title);
        return this;
    }

    public MessageBuilder body(String body) {
        notification.setBody(body);
        return this;
    }

    public String build(String to, @NotNull MessageType messageType) {

        switch (messageType) {
            case TOPIC:
                TopicMessage topicMessage = new TopicMessage();
                topicMessage.setNotification(notification);
                topicMessage.setTopic(to);
                return "{\"message\":" + new Gson().toJson(topicMessage) + "}";
            case SINGLE:
                SingleTargetMessage singleTargetMessage = new SingleTargetMessage();
                singleTargetMessage.setNotification(notification);
                singleTargetMessage.setTarget(to);
                return "{\"message\":" + new Gson().toJson(singleTargetMessage) + "}";
            case CONDITIONAL:
                ConditionalMessage conditionalMessage = new ConditionalMessage();
                conditionalMessage.setCondition(to);
                conditionalMessage.setNotification(notification);
                return "{\"message\":" + new Gson().toJson(conditionalMessage) + "}";
            default:
                throw new IllegalArgumentException("type not defined.");
        }
    }

    public enum MessageType {
        SINGLE, TOPIC, CONDITIONAL
    }
}
