package zlo.projeto.backendtcc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.entities.responsible.Responsible;
import zlo.projeto.backendtcc.exceptions.ServiceException;
import zlo.projeto.backendtcc.repositories.interfaces.devicestorage.IDeviceStorageRepository;
import zlo.projeto.backendtcc.repositories.interfaces.responsible.IResponsibleRepository;
import zlo.projeto.backendtcc.util.MapperUtil;
import zlo.projeto.backendtcc.vo.DeviceStorageVO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DeviceStorageServiceTest {

    private DeviceStorageService service;
    private IDeviceStorageRepository mockDeviceRepo;
    private IResponsibleRepository mockRespRepo;
    private MapperUtil mockMapperUtil;

    @BeforeEach
    public void setUp() {
        // Mocks
        mockDeviceRepo = Mockito.mock(IDeviceStorageRepository.class);
        mockRespRepo = Mockito.mock(IResponsibleRepository.class);
        mockMapperUtil = Mockito.mock(MapperUtil.class);

        // Injeção de dependência no serviço
        service = new DeviceStorageService(mockDeviceRepo, mockRespRepo, mockMapperUtil);
    }


    @Test
    public void testCreateDevice_Success() {
        // Dados do teste
        String token = "abc123";
        String cpf = "98765432100";
        DeviceStorageDTO deviceDTO = new DeviceStorageDTO(token, cpf);

        // Mock do responsável
        Responsible mockResponsible = new Responsible();
        mockResponsible.setCpfRes(cpf);

        // Mock da consulta ao repositório para encontrar o responsável
        Mockito.when(mockRespRepo.findResponsibleByCpf(cpf)).thenReturn(Optional.of(mockResponsible));

        // Mock do comportamento do repositório para salvar o dispositivo
        DeviceStorage savedDevice = new DeviceStorage();
        savedDevice.setTokenDispositivo(token);
        savedDevice.setResponsavel(mockResponsible);

        Mockito.when(mockDeviceRepo.save(Mockito.any(DeviceStorage.class))).thenReturn(savedDevice);

        // Chamada do método de serviço
        DeviceStorage result = service.createDevice(deviceDTO);

        // Verificar que o repositório de responsáveis foi chamado uma vez
        Mockito.verify(mockRespRepo, Mockito.times(1)).findResponsibleByCpf(cpf);

        // Verificar que o dispositivo foi salvo com o token correto
        Mockito.verify(mockDeviceRepo, Mockito.times(1)).save(Mockito.argThat(device -> device.getTokenDispositivo().equals(token)));

        // Verificar o resultado
        assertEquals(token, result.getTokenDispositivo());
        assertEquals(cpf, result.getResponsavel().getCpfRes());
    }

    @Test
    public void testCreateDevice_ResponsibleNotFound() {
        String token = "abc123";
        String cpf = "98765432100";
        DeviceStorageDTO deviceDTO = new DeviceStorageDTO(token, cpf);

        // Mock do repositório para retornar um responsável vazio
        Mockito.when(mockRespRepo.findResponsibleByCpf(cpf)).thenReturn(Optional.empty());

        // Executar o teste e verificar a exceção
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            service.createDevice(deviceDTO);
        });

        // Verificar a mensagem da exceção
        assertEquals(DeviceStorageService.RESPONSIBLE_NOT_FOUND + cpf, exception.getMessage());
    }

    @Test
    public void testCreateDevice_NullCpf() {
        String token = "abc123";
        String cpf = null; // CPF nulo

        DeviceStorageDTO deviceDTO = new DeviceStorageDTO(token, cpf);

        // Testa a exceção de CPF nulo
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            service.createDevice(deviceDTO);
        });

        // Verifica a mensagem de erro
        assertEquals("O CPF do responsável não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void testFindDispositivosByCpfDep_Success() {
        String cpfDep = "12345678900";

        DeviceStorage device1 = new DeviceStorage();
        device1.setId(1);
        device1.setTokenDispositivo("token1");

        DeviceStorage device2 = new DeviceStorage();
        device2.setId(2);
        device2.setTokenDispositivo("token2");

        List<DeviceStorage> devices = List.of(device1, device2);

        // Mock do repositório para retornar os dispositivos
        Mockito.when(mockDeviceRepo.findTokenDispositivosByCpfDep(cpfDep)).thenReturn(devices);

        // Mock do MapperUtil para mapear DeviceStorage para DeviceStorageVO
        DeviceStorageVO deviceVO1 = new DeviceStorageVO();
        deviceVO1.setId(1);
        deviceVO1.setTokenDispositivo("token1");

        DeviceStorageVO deviceVO2 = new DeviceStorageVO();
        deviceVO2.setId(2);
        deviceVO2.setTokenDispositivo("token2");

        Mockito.when(mockMapperUtil.map(device1, DeviceStorageVO.class)).thenReturn(deviceVO1);
        Mockito.when(mockMapperUtil.map(device2, DeviceStorageVO.class)).thenReturn(deviceVO2);

        // Chamada do método de serviço
        List<DeviceStorageVO> result = service.findDispositivosByCpfDep(cpfDep);

        // Verificar que o repositório foi chamado
        Mockito.verify(mockDeviceRepo, Mockito.times(1)).findTokenDispositivosByCpfDep(cpfDep);

        // Verificar o resultado
        assertEquals(2, result.size());
        assertEquals("token1", result.get(0).getTokenDispositivo());
        assertEquals("token2", result.get(1).getTokenDispositivo());
    }

    @Test
    public void testFindDispositivosByCpfDep_NotFound() {
        String cpfDep = "12345678900";

        // Mock do repositório para retornar uma lista vazia
        Mockito.when(mockDeviceRepo.findTokenDispositivosByCpfDep(cpfDep)).thenReturn(List.of());

        // Executar o teste e verificar a exceção
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            service.findDispositivosByCpfDep(cpfDep);
        });

        // Verificar a mensagem da exceção
        assertEquals(DeviceStorageService.DEVICE_NOT_FOUND + cpfDep, exception.getMessage());
    }

}
