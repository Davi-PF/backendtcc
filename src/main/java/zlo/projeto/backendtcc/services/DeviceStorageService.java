package zlo.projeto.backendtcc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zlo.projeto.backendtcc.DTO.DeviceStorageDTO;
import zlo.projeto.backendtcc.data.DeviceStorageVO;
import zlo.projeto.backendtcc.model.DeviceStorage;
import zlo.projeto.backendtcc.model.Responsible;
import zlo.projeto.backendtcc.repositories.DeviceStorageRepository;
import zlo.projeto.backendtcc.repositories.ResponsibleRepository;
import zlo.projeto.backendtcc.repositories.mapper.DozerMapper;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DeviceStorageService {

    private Logger logger = Logger.getLogger(DeviceStorageService.class.getName());

    @Autowired
    DeviceStorageRepository deviceStorageRepository;

    @Autowired
    private ResponsibleRepository responsibleRepository;

    @Autowired
    PagedResourcesAssembler<DeviceStorageVO> assembler;

//    public DeviceStorageVO findDispositivoByCpfDep(String cpfDep) {
//
//        logger.info("Procurando os dispositivos do responsável");
//
//        DeviceStorage result = deviceStorageRepository.findTokenDispositivoByCpfDep(cpfDep)
//                .orElseThrow(() -> new ResourceNotFoundException("No records found for this telephone!"));
//
//        return DozerMapper.parseObject(result, DeviceStorageVO.class);
//    }

    public List<DeviceStorageVO> findDispositivosByCpfDep(String cpfDep) {
        logger.info("Procurando os dispositivos do responsável");

        List<DeviceStorage> devices = deviceStorageRepository.findTokenDispositivosByCpfDep(cpfDep);

        if (devices.isEmpty()) {
            throw new ResourceNotFoundException("No records found for this telephone!");
        }

        return devices.stream()
                .map(device -> DozerMapper.parseObject(device, DeviceStorageVO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DeviceStorage createDevice(DeviceStorageDTO deviceStorageDTO) {

        DeviceStorage deviceStorage = new DeviceStorage();

        // Configura o token
        deviceStorage.setTokenDispositivo(deviceStorageDTO.getTokenDispositivo());

        // Busca o responsável pelo CPF e o associa ao dispositivo
        Responsible responsible = responsibleRepository.findResponsibleByCpf(deviceStorageDTO.getCpfResponsavel())
                .orElseThrow(() -> new IllegalArgumentException("Responsible not found with CPF: " + deviceStorageDTO.getCpfResponsavel()));
        deviceStorage.setCpfResponsavel(responsible);

        return deviceStorageRepository.save(deviceStorage);

//        DeviceStorage entity = DozerMapper.parseObject(deviceVO, DeviceStorage.class);
//        DeviceStorage savedEntity = deviceStorageRepository.save(entity);
//        logger.info("Inserido um dispositivo novo");
//        return DozerMapper.parseObject(savedEntity, DeviceStorageVO.class);
    }

}
