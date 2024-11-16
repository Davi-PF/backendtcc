package zlo.projeto.backendtcc.controllers.devicestorage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.dto.results.StatusResponseDTO;
import zlo.projeto.backendtcc.handlers.devicestorage.DeviceStorageHandler;
import zlo.projeto.backendtcc.services.interfaces.devicestorage.IDeviceStorageService;
import zlo.projeto.backendtcc.vo.DeviceStorageVO;

import java.util.List;

@RestController
@RequestMapping("/api/devicestorage")
public class DeviceStorageController {

    @Autowired
    private IDeviceStorageService deviceStorageService;

    @Autowired
    private DeviceStorageHandler handler;

    @GetMapping("/{cpfDep}")
    @Operation(summary = "Finds devices by responsible CPF", description = "Finds devices associated with a responsible's CPF",
            tags = {"Devices"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = DeviceStorageVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<List<DeviceStorageVO>> findDispositivosByCpfDep(@PathVariable(value = "cpfDep") String cpfDep) {
        List<DeviceStorageVO> deviceStorageVO = deviceStorageService.findDispositivosByCpfDep(cpfDep);
        return ResponseEntity.ok(deviceStorageVO);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Adds a new Device", description = "Adds a new Device by passing in a JSON, XML, or YML representation of the Device!",
            tags = {"Devices"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = DeviceStorageVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<StatusResponseDTO> create(@Valid @RequestBody DeviceStorageDTO deviceStorageDTO) {
        return handler.handleCreate(deviceStorageDTO);
    }

}
