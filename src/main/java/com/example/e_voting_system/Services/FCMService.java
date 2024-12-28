package com.example.e_voting_system.Services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public void sendPushNotification(String fcmToken, String message) {
        Message notification = Message.builder()
                .setToken(fcmToken)
                .putData("message", message)
                .build();
        try {
            FirebaseMessaging.getInstance().send(notification);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send push notification", e);
        }
    }
}
