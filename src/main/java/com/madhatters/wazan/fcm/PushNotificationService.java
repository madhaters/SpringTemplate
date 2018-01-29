package com.madhatters.wazan.fcm;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationService {

    private static final String MESSAGES_URL = "https://fcm.googleapis.com/v1/projects/voip-92b04/messages:send";
    private final static String[] SCOPES = {"https://www.googleapis.com/auth/firebase.messaging"};

    @Async
    public void send(HttpEntity<String> entity) {
        new Thread(() -> {
            try {
                RestTemplate restTemplate = new RestTemplate();

                ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
                interceptors.add(new HeaderRequestInterceptor("Authorization", "Bearer " + getAccessToken()));
                interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
                restTemplate.setInterceptors(interceptors);

                FirebaseResponse firebaseResponse = restTemplate.postForObject(MESSAGES_URL, entity,
                        FirebaseResponse.class);
                CompletableFuture.completedFuture(firebaseResponse);
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
            }
        }).start();

    }

    private static String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream(System.getProperty("user.home") + "/firebase.json"))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        String ac = googleCredential.getAccessToken();
        System.out.println(ac);
        return ac;
    }

}
