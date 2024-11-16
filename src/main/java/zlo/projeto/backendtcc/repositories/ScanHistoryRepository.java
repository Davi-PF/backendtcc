package zlo.projeto.backendtcc.repositories;

import zlo.projeto.backendtcc.entities.ScanHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
}