package com.madhatters.wazan.controllers;

import com.madhatters.wazan.fcm.MessageBuilder;
import com.madhatters.wazan.fcm.PushNotificationService;
import com.madhatters.wazan.model.Notification;
import com.madhatters.wazan.repositories.NotificationRepository;
import com.madhatters.wazan.utils.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    private NotificationRepository repository;
    private PushNotificationService pushNotificationService;

    @Autowired
    public NotificationController(NotificationRepository repository, PushNotificationService notificationService) {
        this.repository = repository;
        this.pushNotificationService = notificationService;
    }

    @PostMapping(value = "/send", produces = MimeTypes.JSON)
    public ResponseEntity<?> send(@RequestBody Notification notification) {
        Notification notification1 = repository.insert(notification);
        sendPushNotification(notification);
        return ResponseEntity.ok(notification1);
    }

    private void sendPushNotification(Notification notification) {
        String message = new MessageBuilder()
                .body(notification.getBody())
                .title(notification.getTitle())
                .build("com.madhatters.wazan", MessageBuilder.MessageType.TOPIC);
        pushNotificationService.send(new HttpEntity<>(message));
    }

    @GetMapping(value = "/get", produces = MimeTypes.JSON)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
