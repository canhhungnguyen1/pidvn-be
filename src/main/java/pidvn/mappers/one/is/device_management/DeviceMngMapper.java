package pidvn.mappers.one.is.device_management;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.UserDto;

import java.util.List;

@Mapper
public interface DeviceMngMapper {
    List<UserDto> getUsers();
    List<DeviceDto> getDeviceTransactions();

}
