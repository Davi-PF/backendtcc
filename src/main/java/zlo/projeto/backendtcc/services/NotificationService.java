package zlo.projeto.backendtcc.services;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import zlo.projeto.backendtcc.entities.notification.NotificationRequest;

@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(NotificationRequest request) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(request.getToken())
                .putData("navigationId", "NotificationTab")
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setChannelId("zlo-app-notification-channel")
                                .build())
                        .build()
                )
                .build();

        // Send the message using FirebaseMessaging
        return firebaseMessaging.send(message);
    }
}