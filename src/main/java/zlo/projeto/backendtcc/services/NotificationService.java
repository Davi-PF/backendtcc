package zlo.projeto.backendtcc.services;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import zlo.projeto.backendtcc.model.NotificationRequest;

@Service
public class NotificationService {

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
        return FirebaseMessaging.getInstance().send(message);
    }
}