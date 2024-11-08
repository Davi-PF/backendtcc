package zlo.projeto.backendtcc.services;

import zlo.projeto.backendtcc.controllers.ResponsibleController;
import zlo.projeto.backendtcc.data.ResponsibleVO;
import zlo.projeto.backendtcc.exceptions.RequiredObjectIsNullException;
import zlo.projeto.backendtcc.model.Responsible;
import zlo.projeto.backendtcc.repositories.ResponsibleRepository;
import zlo.projeto.backendtcc.repositories.mapper.DozerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ResponsibleServices {
    private Logger logger = Logger.getLogger(ResponsibleServices.class.getName());

    @Autowired
    ResponsibleRepository repository;

    @Autowired
    PagedResourcesAssembler<ResponsibleVO> assembler;

    public PagedModel<EntityModel<ResponsibleVO>> findAll(Pageable pageable) {
        logger.info("Finding all responsibles!");

        var responsiblePage = repository.findAll(pageable);
        var responsibleVosPage = responsiblePage.map(p -> DozerMapper.parseObject(p, ResponsibleVO.class));
        responsibleVosPage.map(p -> p.add(linkTo(methodOn(ResponsibleController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(ResponsibleController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(responsibleVosPage, link);
    }

    public PagedModel<EntityModel<ResponsibleVO>> findResponsiblesByName(String firstname, Pageable pageable) {
        logger.info("Finding all people!");

        var responsiblePage = repository.findResponsiblesByName(firstname, pageable);

        var responsibleVosPage = responsiblePage.map(p -> DozerMapper.parseObject(p, ResponsibleVO.class));
        responsibleVosPage.map(p -> p.add(linkTo(methodOn(ResponsibleController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(ResponsibleController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(responsibleVosPage, link);
    }

    public List<Object[]> findResponsiblesCpfAndName(String emailRes, String senhaRes) {
        logger.info("Finding Responsible by E-mail and Password!");

        var responsiblePage = repository.findResponsiblesCpfAndName(emailRes, senhaRes);

        return responsiblePage;
    }

    public ResponsibleVO findById(String id) {

        logger.info("Finding a responsible!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var vo = DozerMapper.parseObject(entity, ResponsibleVO.class);

        vo.add(linkTo(methodOn(ResponsibleController.class).findById(id)).withSelfRel());

        return vo;
    }

    public ResponsibleVO create(ResponsibleVO responsible) {
        if (responsible == null) throw new RequiredObjectIsNullException();

        logger.info("Creating a responsible!");

        var entity = DozerMapper.parseObject(responsible, Responsible.class);

        var vo = DozerMapper.parseObject(repository.save(entity), ResponsibleVO.class);

        vo.add(linkTo(methodOn(ResponsibleController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public ResponsibleVO update(ResponsibleVO responsible) {
        if (responsible == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a responsible!");

        var entity = repository.findById(responsible.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setNomeRes(responsible.getNomeRes());
        entity.setEmailRes(responsible.getEmailRes());
        entity.setContato1Res(responsible.getContato1Res());
        entity.setContato2Res(responsible.getContato2Res());
        entity.setContato3Res(responsible.getContato3Res());
        entity.setIdadeRes(responsible.getIdadeRes());
        entity.setRgRes(responsible.getRgRes());
        entity.setPlanoAssinado(responsible.getPlanoAssinado());
        entity.setEnderecoIdRes(responsible.getEnderecoIdRes());

        var vo = DozerMapper.parseObject(repository.save(entity), ResponsibleVO.class);

        vo.add(linkTo(methodOn(ResponsibleController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }


    public ResponsibleVO updatePassword(ResponsibleVO responsible) {
        if (responsible == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a responsible password!");

        var entity = repository.findById(responsible.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setSenhaRes(responsible.getSenhaRes());

        var vo = DozerMapper.parseObject(repository.save(entity), ResponsibleVO.class);

        vo.add(linkTo(methodOn(ResponsibleController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(String id) {
        logger.info("Deleting a Responsible!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    public ResponsibleVO findByTelefone(String telefone) {
        logger.info("Finding Responsible by Telephone!");

        Responsible result = repository.findResponsibleByTelefone(telefone)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this telephone!"));

        return DozerMapper.parseObject(result, ResponsibleVO.class);
    }

    public ResponsibleVO findByEmail(String email) {
        logger.info("Finding Responsible by Email!");

        Responsible result = repository.findResponsibleByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this e-mail!"));

        return DozerMapper.parseObject(result, ResponsibleVO.class);
    }
}
