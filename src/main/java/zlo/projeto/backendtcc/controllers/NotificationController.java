package zlo.projeto.backendtcc.controllers;

import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zlo.projeto.backendtcc.model.NotificationRequest;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            // Cria a mensagem
            Message message = Message.builder()
                    .setToken(request.getToken())
                    .putData("navigationId", "NotificationTab")
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder()
                                    .setTitle(request.getTitle())
                                    .setBody(request.getBody())
                                    .setChannelId("teste-notificacao")
                                    .build())
                            .build()
                    )
                    .build();

            // Envia a mensagem
            String response = FirebaseMessaging.getInstance().send(message);
            return ResponseEntity.ok(response);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
