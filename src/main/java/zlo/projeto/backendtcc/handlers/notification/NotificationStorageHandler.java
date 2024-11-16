package zlo.projeto.backendtcc.handlers.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationStorageDTO;
import zlo.projeto.backendtcc.dto.results.StatusResponseDTO;
import zlo.projeto.backendtcc.entities.notification.NotificationStorage;
import zlo.projeto.backendtcc.services.NotificationStorageService;
import zlo.projeto.backendtcc.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationStorageHandler {

    private final NotificationStorageService notificationStorageService;
    private final MapperUtil mapperUtil;

    @Autowired
    public NotificationStorageHandler(NotificationStorageService notificationStorageService, MapperUtil mapperUtil) {
        this.notificationStorageService = notificationStorageService;
        this.mapperUtil = mapperUtil;
    }

    public ResponseEntity<StatusResponseDTO> handleCreate(NotificationStorageDTO notificationStorageDTO) {
        NotificationStorage notification = notificationStorageService.storeNotification(notificationStorageDTO);

        // Mapear para VO ou DTO de resposta se necessário
        // Exemplo: NotificationVO notificationVO = mapperUtil.map(notification, NotificationVO.class);

        StatusResponseDTO response = new StatusResponseDTO();
        response.setStatus(200);
        response.setIsOk(true);
        response.setInfoMessage("Notificação criada com sucesso.");
        // response.setContentResponse(notificationVO);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<StatusResponseDTO> handleDelete(Long id) {
        notificationStorageService.deleteNotification(id);

        StatusResponseDTO response = new StatusResponseDTO();
        response.setStatus(200);
        response.setIsOk(true);
        response.setInfoMessage("Notificação removida com sucesso.");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<NotificationStorageDTO>> handleGetByResponsavel(String cpfResponsavel) {
        List<NotificationStorage> notifications = notificationStorageService.getNotificationsByResponsavel(cpfResponsavel);

        List<NotificationStorageDTO> notificationDTOs = notifications.stream()
                .map(notification -> mapperUtil.map(notification, NotificationStorageDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOs);
    }
}

