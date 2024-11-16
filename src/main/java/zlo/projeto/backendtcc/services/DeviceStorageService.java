package zlo.projeto.backendtcc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.repositories.interfaces.responsible.IResponsibleRepository;
import zlo.projeto.backendtcc.vo.DeviceStorageVO;
import zlo.projeto.backendtcc.exceptions.ServiceException;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.entities.responsible.Responsible;
import zlo.projeto.backendtcc.repositories.interfaces.devicestorage.IDeviceStorageRepository;
import zlo.projeto.backendtcc.services.interfaces.devicestorage.IDeviceStorageService;
import zlo.projeto.backendtcc.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceStorageService implements IDeviceStorageService {

    private final IDeviceStorageRepository deviceStorageRepository;
    private final IResponsibleRepository responsibleRepository;
    private final MapperUtil mapperUtil;

    public static final String RESPONSIBLE_NOT_FOUND = "Nenhum responsável encontrado com o CPF: ";
    public static final String DEVICE_NOT_FOUND = "Nenhum dispositivo associado ao CPF: ";

    public DeviceStorageService(IDeviceStorageRepository deviceStorageRepository,
                                IResponsibleRepository responsibleRepository,
                                MapperUtil mapperUtil) {
        this.deviceStorageRepository = deviceStorageRepository;
        this.responsibleRepository = responsibleRepository;
        this.mapperUtil = mapperUtil;
    }

    public List<DeviceStorageVO> findDispositivosByCpfDep(String cpfDep) {
        log.info("Procurando dispositivos associados ao CPF: {}", cpfDep);

        List<DeviceStorage> devices = deviceStorageRepository.findTokenDispositivosByCpfDep(cpfDep);

        if (devices.isEmpty()) {
            throw new ServiceException(DEVICE_NOT_FOUND + cpfDep);
        }

        return devices.stream()
                .map(device -> mapperUtil.map(device, DeviceStorageVO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DeviceStorage createDevice(DeviceStorageDTO deviceStorageDTO) {
        if (deviceStorageDTO.getCpfResponsavel() == null) {
            throw new ServiceException("O CPF do responsável não pode ser nulo.");
        }

        Responsible responsible = responsibleRepository.findResponsibleByCpf(deviceStorageDTO.getCpfResponsavel())
                .orElseThrow(() -> new ServiceException(RESPONSIBLE_NOT_FOUND + deviceStorageDTO.getCpfResponsavel()));

        DeviceStorage deviceStorage = new DeviceStorage();
        deviceStorage.setTokenDispositivo(deviceStorageDTO.getTokenDispositivo());
        deviceStorage.setResponsavel(responsible);

        return deviceStorageRepository.save(deviceStorage);
    }
}
