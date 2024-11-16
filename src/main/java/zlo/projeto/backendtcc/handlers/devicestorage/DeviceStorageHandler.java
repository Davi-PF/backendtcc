package zlo.projeto.backendtcc.handlers.devicestorage;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.dto.results.StatusResponseDTO;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.services.interfaces.devicestorage.IDeviceStorageService;

@Component
public class DeviceStorageHandler {

    private final IDeviceStorageService deviceStorageService;

    public DeviceStorageHandler(IDeviceStorageService deviceStorageService) {
        this.deviceStorageService = deviceStorageService;
    }

    public ResponseEntity<StatusResponseDTO> handleCreate(DeviceStorageDTO deviceStorageDTO) {
        DeviceStorage createdDevice = deviceStorageService.createDevice(deviceStorageDTO);
        StatusResponseDTO<DeviceStorage> response = new StatusResponseDTO<>();
        response.setContentResponse(createdDevice);
        response.setInfoMessage("Dispositivo criado com sucesso.");
        response.setStatusMessage("Success");
        response.setStatus(200);
        response.setIsOk(true);
        return ResponseEntity.ok(response);
    }

    // Outros métodos para find, delete, etc.
}

