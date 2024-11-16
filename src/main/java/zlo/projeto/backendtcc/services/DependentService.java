package zlo.projeto.backendtcc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import zlo.projeto.backendtcc.dto.commands.DependentDTO;
import zlo.projeto.backendtcc.controllers.dependent.DependentController;
import zlo.projeto.backendtcc.entities.dependent.Dependent;
import zlo.projeto.backendtcc.exceptions.RequiredObjectIsNullException;
import zlo.projeto.backendtcc.exceptions.ResourceNotFoundException;
import zlo.projeto.backendtcc.repositories.interfaces.dependent.IDependentRepository;
import zlo.projeto.backendtcc.repositories.interfaces.responsible.IResponsibleRepository;
import zlo.projeto.backendtcc.repositories.mapper.DozerMapper;
import zlo.projeto.backendtcc.services.interfaces.dependent.IDependentService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DependentService implements IDependentService {

    private final Logger logger = Logger.getLogger(DependentService.class.getName());
    private final IDependentRepository repository;
    private final IResponsibleRepository resRepository;
    private final PagedResourcesAssembler<DependentDTO> assembler;

    @Autowired
    public DependentService(IDependentRepository repository,
                            IResponsibleRepository resRepository,
                            PagedResourcesAssembler<DependentDTO> assembler) {
        this.repository = repository;
        this.resRepository = resRepository;
        this.assembler = assembler;
    }

    public PagedModel<EntityModel<DependentDTO>> findAll(Pageable pageable) {
        PagedModel<EntityModel<DependentDTO>> response = null;

        try {
            logger.info("Finding all dependents!");

            var dependentPage = repository.findAll(pageable);
            var dependentVosPage = dependentPage.map(p -> DozerMapper.parseObject(p, DependentDTO.class));
            dependentVosPage.map(p -> p.add(linkTo(methodOn(DependentController.class).findById(p.getKey())).withSelfRel()));

            Link link = linkTo(methodOn(DependentController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
            response = assembler.toModel(dependentVosPage, link);
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public PagedModel<EntityModel<DependentDTO>> findDependentsByName(String firstname, Pageable pageable) {
        PagedModel<EntityModel<DependentDTO>> response = null;

        try {
            logger.info("Finding all people by Name!");

            var dependentPage = repository.findDependentsByName(firstname, pageable);

            var dependentVosPage = dependentPage.map(p -> DozerMapper.parseObject(p, DependentDTO.class));
            dependentVosPage.map(p -> p.add(linkTo(methodOn(DependentController.class).findById(p.getKey())).withSelfRel()));

            Link link = linkTo(methodOn(DependentController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
            response = assembler.toModel(dependentVosPage, link);
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public PagedModel<EntityModel<DependentDTO>> findDependentsByCpfRes(String cpfRes, Pageable pageable) {
        PagedModel<EntityModel<DependentDTO>> response = null;

        try {
            logger.info("Finding all people by CpfRes!");

            var dependentPage = repository.findDependentsByCpfRes(cpfRes, pageable);

            var dependentVosPage = dependentPage.map(p -> DozerMapper.parseObject(p, DependentDTO.class));
            dependentVosPage.map(p -> p.add(linkTo(methodOn(DependentController.class).findById(p.getKey())).withSelfRel()));

            Link link = linkTo(methodOn(DependentController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
            response = assembler.toModel(dependentVosPage, link);
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public DependentDTO findById(String id) {
        DependentDTO response = null;

        try {
            logger.info("Finding a dependent!");

            var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

            var vo = DozerMapper.parseObject(entity, DependentDTO.class);

            vo.add(linkTo(methodOn(DependentController.class).findById(id)).withSelfRel());

            response = vo;

        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public Map<String, String> verifyDependentsCpfAndEmergPhone(String cpfDep, String emergPhone) {
        Map<String, String> response = null;

        try {
            logger.info("Verifying Dependents CPF and Emergency Phone!");

            String emergePhoneByCpf = resRepository.findResponsibleEmergPhoneByCpfDep(cpfDep);
            String emergePhoneByPath = emergPhone;
            if (emergePhoneByCpf.equals(emergePhoneByPath)) {
                Map<String, String> data = new HashMap<>();

                data.put("emergPhone", emergPhone);
                data.put("cpfDep", cpfDep);

                response = data;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public DependentDTO create(DependentDTO dependent) {
        DependentDTO response = null;

        try {
            if (dependent == null) throw new RequiredObjectIsNullException();

            logger.info("Creating a dependent!");

            var entity = DozerMapper.parseObject(dependent, Dependent.class);

            var vo = DozerMapper.parseObject(repository.save(entity), DependentDTO.class);

            vo.add(linkTo(methodOn(DependentController.class).findById(vo.getKey())).withSelfRel());

            response = vo;
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public DependentDTO update(DependentDTO dependent) {
        DependentDTO response = null;

        try {
            if (dependent == null) throw new RequiredObjectIsNullException();

            logger.info("Updating a dependent!");

            var entity = repository.findById(dependent.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

            entity.setNomeDep(dependent.getNomeDep());
            entity.setIdadeDep(dependent.getIdadeDep());
            entity.setTipoSanguineo(dependent.getTipoSanguineo());
            entity.setLaudo(dependent.getLaudo());
            entity.setGeneroDep(dependent.getGeneroDep());
            entity.setRgDep(dependent.getRgDep());
            entity.setCpfResDep(dependent.getCpfResDep());
            entity.setPiTagIdDep(dependent.getPiTagIdDep());
            entity.setCpfTerDep(dependent.getCpfTerDep());
            entity.setIdCirurgiaDep(dependent.getIdCirurgiaDep());
            entity.setIdScanDep(dependent.getIdScanDep());

            var vo = DozerMapper.parseObject(repository.save(entity), DependentDTO.class);

            vo.add(linkTo(methodOn(DependentController.class).findById(vo.getKey())).withSelfRel());

            response = vo;

        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public String delete(String id) {
        String response = null;

        try {
            logger.info("Deleting a Dependent!");

            var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

            repository.delete(entity);

            response = id;
        } catch (Exception e) {
            return null;
        }

        return response;
    }
}