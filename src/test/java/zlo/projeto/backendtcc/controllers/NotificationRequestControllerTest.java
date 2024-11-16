package zlo.projeto.backendtcc.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zlo.projeto.backendtcc.controllers.notification.NotificationRequestController;
import zlo.projeto.backendtcc.entities.notification.NotificationRequest;
import zlo.projeto.backendtcc.services.NotificationRequestService;

@WebMvcTest(NotificationRequestController.class)
public class NotificationRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationRequestService notificationRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendNotification_Success() throws Exception {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setToken("test_token");
        request.setTitle("Test Title");
        request.setBody("Test Body");

        when(notificationRequestService.sendNotification(any(NotificationRequest.class)))
                .thenReturn("Notification sent successfully");

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully"));
    }

    @Test
    public void testSendNotification_InternalServerError() throws Exception {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setToken("test_token");
        request.setTitle("Test Title");
        request.setBody("Test Body");

        when(notificationRequestService.sendNotification(any(NotificationRequest.class)))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error sending notification: Service error"));
    }
}
