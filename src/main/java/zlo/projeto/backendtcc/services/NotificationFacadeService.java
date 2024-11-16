package zlo.projeto.backendtcc.services;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationRequestDTO;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationStorageDTO;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.entities.notification.NotificationRequest;
import zlo.projeto.backendtcc.entities.responsible.Responsible;
import zlo.projeto.backendtcc.exceptions.ServiceException;
import zlo.projeto.backendtcc.repositories.interfaces.devicestorage.IDeviceStorageRepository;
import zlo.projeto.backendtcc.repositories.interfaces.responsible.IResponsibleRepository;

import java.util.List;

@Service
@Slf4j
public class NotificationFacadeService {

    private final NotificationRequestService notificationRequestService;
    private final NotificationStorageService notificationStorageService;
    private final IResponsibleRepository responsibleRepository;
    private final IDeviceStorageRepository deviceStorageRepository;

    @Autowired
    public NotificationFacadeService(NotificationRequestService notificationRequestService,
                                     NotificationStorageService notificationStorageService,
                                     IResponsibleRepository responsibleRepository,
                                     IDeviceStorageRepository deviceStorageRepository) {
        this.notificationRequestService = notificationRequestService;
        this.notificationStorageService = notificationStorageService;
        this.responsibleRepository = responsibleRepository;
        this.deviceStorageRepository = deviceStorageRepository;

    }

    public void sendAndStoreNotification(NotificationRequestDTO notificationRequestDTO) {
        // Verificar se o responsável existe
        Responsible responsible = responsibleRepository.findById(notificationRequestDTO.getCpfResponsavel())
                .orElseThrow(() -> new ServiceException("Responsável não encontrado com CPF: " + notificationRequestDTO.getCpfResponsavel()));

        // Obter os dispositivos do responsável
        List<DeviceStorage> devices = deviceStorageRepository.findByResponsavel(responsible);

        if (devices.isEmpty()) {
            throw new ServiceException("Nenhum dispositivo encontrado para o responsável com CPF: " + notificationRequestDTO.getCpfResponsavel());
        }

        // Enviar a notificação para cada dispositivo
        for (DeviceStorage device : devices) {
            try {
                NotificationRequest notificationRequest = new NotificationRequest();
                notificationRequest.setTitle(notificationRequestDTO.getTitle());
                notificationRequest.setBody(notificationRequestDTO.getBody());
                notificationRequest.setToken(device.getTokenDispositivo());

                notificationRequestService.sendNotification(notificationRequest);
            } catch (FirebaseMessagingException e) {
                // Logar o erro e continuar com o próximo dispositivo
                log.error("Erro ao enviar notificação para o dispositivo {}: {}", device.getId(), e.getMessage());
            }
        }

        // Armazenar a notificação
        NotificationStorageDTO notificationStorageDTO = new NotificationStorageDTO();
        notificationStorageDTO.setTitulo(notificationRequestDTO.getTitle());
        notificationStorageDTO.setMensagem(notificationRequestDTO.getBody());
        notificationStorageDTO.setCpfResponsavel(notificationRequestDTO.getCpfResponsavel());

        notificationStorageService.storeNotification(notificationStorageDTO);
    }

}
