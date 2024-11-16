package zlo.projeto.backendtcc.services.interfaces.responsible;

import zlo.projeto.backendtcc.dto.commands.ResponsibleDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface IResponsibleService {
    PagedModel<EntityModel<ResponsibleDTO>> findAll(Pageable pageable);
    PagedModel<EntityModel<ResponsibleDTO>> findResponsiblesByName(String firstname, Pageable pageable);
    List<Object[]> findResponsiblesCpfAndName(String emailRes, String senhaRes);
    ResponsibleDTO findById(String id);
    ResponsibleDTO create(ResponsibleDTO responsible);
    ResponsibleDTO update(ResponsibleDTO responsible);
    String delete(String id);
    ResponsibleDTO updatePassword(ResponsibleDTO responsible);
    ResponsibleDTO findByTelefone(String telefone);
    ResponsibleDTO findByEmail(String email);
}