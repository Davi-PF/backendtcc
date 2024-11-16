package zlo.projeto.backendtcc.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.auth.oauth2.GoogleCredentials;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import zlo.projeto.backendtcc.entities.notification.NotificationRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @BeforeAll
    public static void setupFirebase() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(firebaseMessaging);  // Inject the mock
    }

    @Test
    public void testSendNotification_Success() throws FirebaseMessagingException {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setToken("mockToken123456");  // Usa um valor fictício para o token
        request.setTitle("Test Title");
        request.setBody("Test Body");

        // Configura o mock para retornar uma ID simulada para a mensagem
        when(firebaseMessaging.send(any(Message.class))).thenReturn("mockedMessageId");

        // Act
        String response = notificationService.sendNotification(request);

        // Assert
        assertEquals("mockedMessageId", response);  // Verifica se o retorno é o ID simulado
        verify(firebaseMessaging, times(1)).send(any(Message.class));  // Garante que o método send foi chamado uma vez
    }

    @Test
    public void testSendNotification_FirebaseMessagingException() throws FirebaseMessagingException {
        NotificationRequest request = new NotificationRequest();
        request.setToken("mockToken123456");
        request.setTitle("Test Title");
        request.setBody("Test Body");

        FirebaseMessagingException mockException = mock(FirebaseMessagingException.class);
        when(mockException.getMessage()).thenReturn("Mocked exception");
        when(firebaseMessaging.send(any(Message.class))).thenThrow(mockException);

        FirebaseMessagingException exception = assertThrows(FirebaseMessagingException.class, () -> notificationService.sendNotification(request));

        assertEquals("Mocked exception", exception.getMessage());
        verify(firebaseMessaging, times(1)).send(any(Message.class));
    }
}
