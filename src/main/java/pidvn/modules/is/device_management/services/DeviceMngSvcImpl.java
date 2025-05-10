package pidvn.modules.is.device_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pidvn.entities.one.IsDevice;
import pidvn.mappers.one.is.device_management.DeviceMngMapper;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.UserDto;
import pidvn.repositories.one.IsDeviceRepo;

import java.util.List;

@Service
public class DeviceMngSvcImpl implements DeviceMngSvc {

    @Autowired
    private IsDeviceRepo isDeviceRepo;

    @Autowired
    private DeviceMngMapper deviceMngMapper;

    @Override
    public List<IsDevice> getDevices() {
        return this.isDeviceRepo.findAll();
    }

    @Override
    public List<UserDto> getUsers() {
        return this.deviceMngMapper.getUsers();
    }

    @Override
    public List<DeviceDto> getDeviceTransactions() {
        return List.of();
    }


}
