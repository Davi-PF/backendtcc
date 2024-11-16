package zlo.projeto.backendtcc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.controllers.devicestorage.DeviceStorageController;
import zlo.projeto.backendtcc.vo.DeviceStorageVO;
import zlo.projeto.backendtcc.exceptions.ResourceNotFoundException;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.services.DeviceStorageService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceStorageController.class)
public class DeviceStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceStorageService deviceStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindDispositivosByCpfDep_Success() throws Exception {
        String cpfDep = "12345678901";

        DeviceStorageVO device1 = new DeviceStorageVO(1, "token1", Instant.now());
        DeviceStorageVO device2 = new DeviceStorageVO(2, "token2", Instant.now());

        List<DeviceStorageVO> devices = Arrays.asList(device1, device2);

        when(deviceStorageService.findDispositivosByCpfDep(cpfDep)).thenReturn(devices);

        mockMvc.perform(get("/api/devicestorage/{cpfDep}", cpfDep)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tokenDispositivo").value("token1"))
                .andExpect(jsonPath("$[1].tokenDispositivo").value("token2"));
    }

    @Test
    void testFindDispositivosByCpfDep_NotFound() throws Exception {
        String cpfDep = "99999999999";

        when(deviceStorageService.findDispositivosByCpfDep(cpfDep))
                .thenThrow(new ResourceNotFoundException("No records found for this CPF: " + cpfDep));

        mockMvc.perform(get("/api/devicestorage/{cpfDep}", cpfDep)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateDevice_Success() throws Exception {
        DeviceStorageDTO deviceDTO = new DeviceStorageDTO("token123", "12345678901");

        DeviceStorage device = new DeviceStorage();
        device.setId(1);
        device.setTokenDispositivo("token123");
        device.setDataCadastro(Instant.now());

        when(deviceStorageService.createDevice(any(DeviceStorageDTO.class))).thenReturn(device);

        mockMvc.perform(post("/api/devicestorage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenDispositivo").value("token123"));
    }

    @Test
    void testCreateDevice_ValidationError() throws Exception {
        DeviceStorageDTO deviceDTO = new DeviceStorageDTO("", ""); // Campos inválidos

        mockMvc.perform(post("/api/devicestorage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tokenDispositivo").value("Token do dispositivo não pode ser vazio"))
                .andExpect(jsonPath("$.cpfResponsavel").value("CPF do responsável não pode ser vazio"));

        // Verifica que o serviço não foi chamado
        Mockito.verifyNoInteractions(deviceStorageService);
    }
}
