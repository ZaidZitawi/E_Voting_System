package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Notification;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FCMService fcmService; // Handles push notifications

    public NotificationService(NotificationRepository notificationRepository, FCMService fcmService) {
        this.notificationRepository = notificationRepository;
        this.fcmService = fcmService;
    }

    // Notify eligible users
    public void notifyUsers(List<User> users, String message) {
        for (User user : users) {
            // Send push notification using FCM
            if (user.getFcmToken() != null) {
                fcmService.sendPushNotification(user.getFcmToken(), "New Election" + message);
            }

            // Optional: Save notification in the database
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setIsRead(false);
            notificationRepository.save(notification);
        }
    }
}
