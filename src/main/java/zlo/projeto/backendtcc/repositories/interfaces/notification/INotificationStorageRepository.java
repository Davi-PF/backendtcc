package zlo.projeto.backendtcc.repositories.interfaces.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import zlo.projeto.backendtcc.entities.notification.NotificationStorage;

import java.util.List;

public interface INotificationStorageRepository extends JpaRepository<NotificationStorage, Long> {
    List<NotificationStorage> findByResponsavelCpfRes(String cpfRes);
}

