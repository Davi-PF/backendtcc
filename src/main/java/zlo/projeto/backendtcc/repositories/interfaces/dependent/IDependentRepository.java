package zlo.projeto.backendtcc.repositories.interfaces.dependent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zlo.projeto.backendtcc.entities.dependent.Dependent;

@Repository
public interface IDependentRepository extends JpaRepository<Dependent, String> {
    @Query("SELECT d FROM Dependent d WHERE d.nomeDep LIKE LOWER(CONCAT ('%',:nomeDep,'%'))")
    Page<Dependent> findDependentsByName(@Param("nomeDep") String firstName, Pageable pageable);

    @Query("SELECT d FROM Dependent d WHERE d.cpfResDep LIKE :cpfResDep")
    Page<Dependent> findDependentsByCpfRes(@Param("cpfResDep") String cpfResDep, Pageable pageable);
}