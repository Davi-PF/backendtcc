package zlo.projeto.backendtcc.services.interfaces.dependent;

import zlo.projeto.backendtcc.dto.commands.DependentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.Map;

public interface IDependentService {
    PagedModel<EntityModel<DependentDTO>> findAll(Pageable pageable);
    PagedModel<EntityModel<DependentDTO>> findDependentsByName(String firstname, Pageable pageable);
    PagedModel<EntityModel<DependentDTO>> findDependentsByCpfRes(String cpfRes, Pageable pageable);
    DependentDTO findById(String id);
    Map<String, String> verifyDependentsCpfAndEmergPhone(String cpfDep, String emergPhone);
    DependentDTO create(DependentDTO dependent);
    DependentDTO update(DependentDTO dependent);
    String delete(String id);
}