package zlo.projeto.backendtcc.controllers;

import zlo.projeto.backendtcc.data.ResponsibleVO;
import zlo.projeto.backendtcc.services.ResponsibleServices;
import zlo.projeto.backendtcc.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/responsibles/")
@Tag(name = "Responsible", description = "Endpoints for Managing Responsibles")
public class ResponsibleController {

    private Logger logger = Logger.getLogger(ResponsibleServices.class.getName());

    @Autowired
    private ResponsibleServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Responsibles", description = "Finds all Responsibles",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ResponsibleVO.class)
                                            )
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<ResponsibleVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equals(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nomeRes"));

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/findResponsiblesByName/{nomeRes}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds Responsibles by Name", description = "Finds Responsibles by Name",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ResponsibleVO.class)
                                            )
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<ResponsibleVO>>> findResponsiblesByName(
            @PathVariable(value = "nomeRes") String nomeRes,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equals(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nomeRes"));

        return ResponseEntity.ok(service.findResponsiblesByName(nomeRes, pageable));
    }

    @GetMapping(value = "/findResponsiblesCpfAndName/params", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds Responsibles by Email and Password", description = "Finds Responsibles by Email and Password",
            tags = {"Responsible"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ResponsibleVO.class)
                                            )
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public List<Object[]> findResponsiblesCpfAndName(
            @RequestParam(value = "emailRes") String emailRes,
            @RequestParam(value = "senhaRes") String senhaRes
    ) {
        return service.findResponsiblesCpfAndName(emailRes, senhaRes);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Finds a Responsible", description = "Finds a Responsible",
            tags = {"Responsible"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponsibleVO findById(@PathVariable(value = "id") String id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a new Responsible", description = "Adds a new Responsible by passing in a JSON, XML or YML representation of the Responsible!",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponsibleVO create(@RequestBody ResponsibleVO responsibleVO) {
        return service.create(responsibleVO);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a Responsible", description = "Updates a Responsible by passing in a JSON, XML or YML representation of the Responsible!",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponsibleVO update(@RequestBody ResponsibleVO responsibleVO) {
        return service.update(responsibleVO);
    }

    @PutMapping(
            value = "/updatePassword",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a Responsible", description = "Updates a Responsible by passing in a JSON, XML or YML representation of the Responsible!",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponsibleVO updatePassword(@RequestBody ResponsibleVO responsibleVO) {
        return service.updatePassword(responsibleVO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a Responsible", description = "Deletes a Responsible by passing in a JSON, XML or YML representation of the Responsible!",
            tags = {"Responsibles"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/findByTelefone/{telefone}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds Responsible by Telephone", description = "Finds Responsible by any of their Telephone numbers",
            tags = {"Responsible"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<ResponsibleVO> findByTelefone(@PathVariable(value = "telefone") String telefone) {
        ResponsibleVO responsible = service.findByTelefone(telefone);
        if (responsible != null) {
            return ResponseEntity.ok(responsible);
        } else {
            // Directly return a 404 Not Found response if the responsible is not found
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/findByEmail/{email}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds Responsible by Email", description = "Finds Responsible by his Email",
            tags = {"Responsible"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponsibleVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<ResponsibleVO> findByEmail(@PathVariable(value = "email") String emailRes) {
        ResponsibleVO responsible = service.findByEmail(emailRes);
        if (responsible != null) {
            return ResponseEntity.ok(responsible);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
