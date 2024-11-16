package zlo.projeto.backendtcc.controllers.notification;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationStorageDTO;
import zlo.projeto.backendtcc.dto.results.StatusResponseDTO;
import zlo.projeto.backendtcc.entities.notification.NotificationStorage;
import zlo.projeto.backendtcc.handlers.notification.NotificationStorageHandler;
import zlo.projeto.backendtcc.services.NotificationStorageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationStorageController {

    private final NotificationStorageHandler notificationStorageHandler;
    private final NotificationStorageService notificationStorageService;

    @Autowired
    public NotificationStorageController(NotificationStorageHandler notificationStorageHandler, NotificationStorageService notificationStorageService) {
        this.notificationStorageHandler = notificationStorageHandler;
        this.notificationStorageService = notificationStorageService;
    }

    @PostMapping
    public ResponseEntity<StatusResponseDTO> createNotification(@RequestBody NotificationStorageDTO notificationDTO) {
        return notificationStorageHandler.handleCreate(notificationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> deleteNotification(@PathVariable Long id) {
        return notificationStorageHandler.handleDelete(id);
    }

    @GetMapping("/responsavel/{cpfResponsavel}")
    public ResponseEntity<List<NotificationStorageDTO>> getNotificationsByResponsavel(@PathVariable String cpfResponsavel) {
        List<NotificationStorage> notifications = notificationStorageService.getNotificationsByResponsavel(cpfResponsavel);

        List<NotificationStorageDTO> notificationDTOs = notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOs);
    }


    private NotificationStorageDTO mapToDTO(NotificationStorage notification) {
        NotificationStorageDTO dto = new NotificationStorageDTO();
        dto.setTitulo(notification.getTitulo());
        dto.setMensagem(notification.getMensagem());
        dto.setCpfResponsavel(notification.getResponsavel() != null ? notification.getResponsavel().getCpfRes() : null);
        dto.setDataEnvio(notification.getDataEnvio());
        dto.setLida(notification.getLida());
        // Outros campos, se houver
        return dto;
    }
}
