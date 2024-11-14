package zlo.projeto.backendtcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import zlo.projeto.backendtcc.model.DeviceStorage;

import java.util.List;

@Repository
public interface DeviceStorageRepository extends JpaRepository<DeviceStorage, String> {

    @Query("SELECT d FROM DeviceStorage d " +
            "JOIN d.cpfResponsavel r " +
            "JOIN Dependent dep ON r.cpfRes = dep.cpfResDep " +
            "WHERE dep.cpfDep = :cpfDep")
    List<DeviceStorage> findTokenDispositivosByCpfDep(@Param("cpfDep") String cpfDep);

}
