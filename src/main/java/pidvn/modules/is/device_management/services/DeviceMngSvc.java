package pidvn.modules.is.device_management.services;

import pidvn.entities.one.IsDevice;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.UserDto;

import java.util.List;

public interface DeviceMngSvc {
    List<IsDevice> getDevices();
    List<UserDto> getUsers();
    List<DeviceDto> getDeviceTransactions();
}
