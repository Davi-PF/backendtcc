package zlo.projeto.backendtcc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zlo.projeto.backendtcc.dto.commands.notification.NotificationStorageDTO;
import zlo.projeto.backendtcc.entities.notification.NotificationStorage;
import zlo.projeto.backendtcc.entities.responsible.Responsible;
import zlo.projeto.backendtcc.exceptions.ServiceException;
import zlo.projeto.backendtcc.repositories.interfaces.notification.INotificationStorageRepository;
import zlo.projeto.backendtcc.repositories.interfaces.responsible.IResponsibleRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class NotificationStorageService {

    private final INotificationStorageRepository notificationStorageRepository;
    private final IResponsibleRepository responsibleRepository;

    @Autowired
    public NotificationStorageService(INotificationStorageRepository notificationStorageRepository,
                               IResponsibleRepository responsibleRepository) {
        this.notificationStorageRepository = notificationStorageRepository;
        this.responsibleRepository = responsibleRepository;
    }

    public NotificationStorage storeNotification(NotificationStorageDTO notificationDTO) {
        // Verificar se o responsável existe
        Responsible responsible = responsibleRepository.findById(notificationDTO.getCpfResponsavel())
                .orElseThrow(() -> new ServiceException("Responsável não encontrado com CPF: " + notificationDTO.getCpfResponsavel()));

        // Criar a entidade Notification
        NotificationStorage notification = new NotificationStorage();
        notification.setTitulo(notificationDTO.getTitulo());
        notification.setMensagem(notificationDTO.getMensagem());
        notification.setDataEnvio(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        notification.setLida(false);
        notification.setResponsavel(responsible);

        // Salvar a notificação
        return notificationStorageRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        NotificationStorage notification = notificationStorageRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Notificação não encontrada com ID: " + id));

        notificationStorageRepository.delete(notification);
    }

    public List<NotificationStorage> getNotificationsByResponsavel(String cpfResponsavel) {
        return notificationStorageRepository.findByResponsavelCpfRes(cpfResponsavel);
    }

}
