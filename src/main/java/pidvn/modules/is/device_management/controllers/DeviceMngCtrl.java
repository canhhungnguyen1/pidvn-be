package pidvn.modules.is.device_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IsDevice;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.TransactionDto;
import pidvn.modules.is.device_management.models.UserDto;
import pidvn.modules.is.device_management.services.DeviceMngSvcImpl;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("IS/DeviceManagement")
public class DeviceMngCtrl {

    @Autowired
    private DeviceMngSvcImpl deviceMngSvc;

    /**
     * API: IS/DeviceManagement/Devices
     *
     * @return
     */
    @GetMapping("Devices")
    public ResponseEntity<ApiResponse<?>> getDevices() {
        ApiResponse<List<DeviceDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDevices());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IS/DeviceManagement/Users
     *
     * @return
     */
    @GetMapping("Users")
    public ResponseEntity<ApiResponse<?>> getUsers() {
        ApiResponse<List<UserDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getUsers());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IS/DeviceManagement/Transactions
     *
     * @return
     */
    @GetMapping("Transactions")
    public ResponseEntity<ApiResponse<?>> getDeviceTransactions() {
        ApiResponse<List<TransactionDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDeviceTransactions());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IS/DeviceManagement/Transaction
     *
     * @return
     */
    @PostMapping("Transaction")
    public ResponseEntity<ApiResponse<?>> saveTransaction(@RequestBody TransactionDto transactionDto) throws MessagingException, UnsupportedEncodingException {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.saveTransaction(transactionDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IS/DeviceManagement/Inventory/Requests
     *
     * @return
     */
    @GetMapping("Inventory/Requests")
    public ResponseEntity<ApiResponse<?>> getInventoryRequests() {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
