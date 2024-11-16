package zlo.projeto.backendtcc.services.interfaces.devicestorage;

import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;
import zlo.projeto.backendtcc.entities.devicestorage.DeviceStorage;
import zlo.projeto.backendtcc.vo.DeviceStorageVO;

import java.util.List;

public interface IDeviceStorageService {
    List<DeviceStorageVO> findDispositivosByCpfDep(String cpfDep);
    DeviceStorage createDevice(DeviceStorageDTO deviceStorageDTO);
}
