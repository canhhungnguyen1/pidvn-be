package pidvn.modules.is.device_management.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pidvn.entities.one.IsDevice;
import pidvn.entities.one.IsDeviceTransaction;
import pidvn.mappers.one.is.device_management.DeviceMngMapper;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.TransactionDto;
import pidvn.modules.is.device_management.models.UserDto;
import pidvn.repositories.one.IsDeviceRepo;
import pidvn.repositories.one.IsDeviceTransactionRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceMngSvcImpl implements DeviceMngSvc {

    @Autowired
    private IsDeviceRepo isDeviceRepo;

    @Autowired
    private DeviceMngMapper deviceMngMapper;

    @Autowired
    private IsDeviceTransactionRepo isDeviceTransactionRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DeviceDto> getDevices() {
        return this.deviceMngMapper.getDevices();
    }

    @Override
    public List<UserDto> getUsers() {
        return this.deviceMngMapper.getUsers();
    }

    @Override
    public List<TransactionDto> getDeviceTransactions() {
        return this.deviceMngMapper.getDeviceTransactions();
    }

    @Override
    public Map<String, Object> saveTransaction(TransactionDto transactionDto) {

        // Thêm data vào bảng is_device_transaction
        IsDeviceTransaction transaction = this.modelMapper.map(transactionDto, IsDeviceTransaction.class);
        IsDeviceTransaction response1 = this.isDeviceTransactionRepo.save(transaction);

        // Cập nhật transaction_id vào bảng is_device
        IsDevice device = this.isDeviceRepo.findByName(transaction.getDeviceName());
        device.setTransactionId(response1.getId());
        IsDevice response2 = this.isDeviceRepo.save(device);

        Map<String, Object> result = new HashMap<>();
        result.put("transaction", response1);
        result.put("device", response2);

        return result;
    }


}
