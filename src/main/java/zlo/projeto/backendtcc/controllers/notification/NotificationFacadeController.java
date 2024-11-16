package zlo.projeto.backendtcc.controllers.notification;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationRequestDTO;
import zlo.projeto.backendtcc.dto.results.StatusResponseDTO;
import zlo.projeto.backendtcc.services.NotificationFacadeService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationFacadeController {

    private final NotificationFacadeService notificationFacadeService;

    @Autowired
    public NotificationFacadeController(NotificationFacadeService notificationFacadeService) {
        this.notificationFacadeService = notificationFacadeService;
    }

    @PostMapping("/send-and-store")
    public ResponseEntity<StatusResponseDTO> sendAndStoreNotification(@Valid @RequestBody NotificationRequestDTO notificationRequestDTO) {
        notificationFacadeService.sendAndStoreNotification(notificationRequestDTO);

        StatusResponseDTO response = new StatusResponseDTO();
        response.setStatus(200);
        response.setIsOk(true);
        response.setInfoMessage("Notificação enviada e armazenada com sucesso.");

        return ResponseEntity.ok(response);
    }

    // Outros endpoints relacionados ao armazenamento de notificações, se necessário
}


