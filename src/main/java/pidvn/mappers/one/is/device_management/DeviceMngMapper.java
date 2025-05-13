package pidvn.mappers.one.is.device_management;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.InventoryDataDto;
import pidvn.modules.is.device_management.models.TransactionDto;
import pidvn.modules.is.device_management.models.UserDto;

import java.util.List;

@Mapper
public interface DeviceMngMapper {
    List<DeviceDto> getDevices();
    List<UserDto> getUsers();
    List<TransactionDto> getDeviceTransactions();
    List<InventoryDataDto> getInventoryData(Integer requestId);
}
