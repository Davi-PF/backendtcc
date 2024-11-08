package zlo.projeto.backendtcc.controllers;

import zlo.projeto.backendtcc.data.DependentVO;
import zlo.projeto.backendtcc.data.DependentWebDataVO;
import zlo.projeto.backendtcc.data.ResponsibleVO;
import zlo.projeto.backendtcc.services.MixedServices;
import zlo.projeto.backendtcc.services.ResponsibleServices;
import zlo.projeto.backendtcc.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/responsiblesAndDependents/")
@Tag(name = "Responsible and Dependent", description = "Endpoints for Managing Responsibles and Dependents")
public class MixedController {

    private Logger logger = Logger.getLogger(ResponsibleServices.class.getName());

    @Autowired
    private MixedServices service;

    @GetMapping(
            value = "/params",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Finds a Dependent", description = "Finds a Dependent",
            tags = {"Dependent"},
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
    public DependentVO findByIdWithSecurity(@RequestParam(value = "idDep") String idDep, @RequestParam(value = "emergPhone") String emergPhone) {
        return service.findByIdWithSecurity(idDep, emergPhone);
    }

    @GetMapping(
            value = "/webdata/params",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Finds a Dependent", description = "Finds a Dependent",
            tags = {"Dependent"},
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
    public DependentWebDataVO findWebDataByIdWithSecurity(@RequestParam(value = "idDep") String idDep, @RequestParam(value = "emergPhone") String emergPhone) {
        return service.findWebDataByIdWithSecurity(idDep, emergPhone);
    }
}
