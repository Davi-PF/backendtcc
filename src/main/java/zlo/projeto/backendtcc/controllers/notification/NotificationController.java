package zlo.projeto.backendtcc.controllers.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zlo.projeto.backendtcc.entities.notification.NotificationRequest;
import zlo.projeto.backendtcc.services.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a Notification", description = "Sends a notification to a specified token",
            tags = {"Notifications"},
            responses = {
                    @ApiResponse(description = "Notification sent successfully", responseCode = "200"),
                    @ApiResponse(description = "Error sending notification", responseCode = "500")
            })
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            String response = notificationService.sendNotification(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending notification: " + e.getMessage());
        }
    }
}