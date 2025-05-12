package pidvn.modules.is.device_management.services;

import pidvn.entities.one.IsDevice;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.TransactionDto;
import pidvn.modules.is.device_management.models.UserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface DeviceMngSvc {
    List<DeviceDto> getDevices();
    DeviceDto getDevice(String deviceName) throws Exception;
    List<UserDto> getUsers();
    List<TransactionDto> getDeviceTransactions();
    Map<String, Object> saveTransaction(TransactionDto transactionDto) throws MessagingException, UnsupportedEncodingException;
}
