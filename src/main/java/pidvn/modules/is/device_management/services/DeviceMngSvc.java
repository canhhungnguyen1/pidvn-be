package pidvn.modules.is.device_management.services;

import pidvn.entities.one.IsDevice;
import pidvn.entities.one.IsDeviceLocation;
import pidvn.modules.is.device_management.models.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface DeviceMngSvc {
    List<IsDeviceLocation> getLocations();
    List<UserDto> getUsers();
    List<DeviceDto> getDevices();
    DeviceDto getDevice(String deviceName) throws Exception;
    List<TransactionDto> getDeviceTransactions();
    Map<String, Object> saveTransaction(TransactionDto transactionDto) throws MessagingException, UnsupportedEncodingException;
    InventoryRequestDto createInventoryRequest(InventoryRequestDto inventoryRequestDto);
    List<InventoryRequestDto> getInventoryRequests();
    InventoryDataDto saveInventoryData(InventoryDataDto ivtDataDto);
    List<InventoryDataDto> getInventoryData(Integer requestId);
}
