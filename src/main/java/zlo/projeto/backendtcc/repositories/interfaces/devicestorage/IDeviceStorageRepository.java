package zlo.projeto.backendtcc.repositories.interfaces.devicestorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.entities.responsible.Responsible;

import java.util.List;

@Repository
public interface IDeviceStorageRepository extends JpaRepository<DeviceStorage, Integer> {

    @Query("SELECT d FROM DeviceStorage d " +
            "JOIN d.responsavel r " +
            "JOIN Dependent dep ON dep.cpfResDep = r.cpfRes " +
            "WHERE dep.cpfDep = :cpfDep")
    List<DeviceStorage> findTokenDispositivosByCpfDep(String cpfDep);

    List<DeviceStorage> findByResponsavel(Responsible responsavel);

}

