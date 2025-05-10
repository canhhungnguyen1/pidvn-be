package pidvn.modules.is.device_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IsDevice;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.UserDto;
import pidvn.modules.is.device_management.services.DeviceMngSvcImpl;

import java.util.List;

@RestController
@RequestMapping("IS/DeviceManagement")
public class DeviceMngCtrl {

    @Autowired
    private DeviceMngSvcImpl deviceMngSvc;

    @GetMapping("Devices")
    public ResponseEntity<ApiResponse<?>> getDevices() {
        ApiResponse<List<IsDevice>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDevices());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("Users")
    public ResponseEntity<ApiResponse<?>> getUsers() {
        ApiResponse<List<UserDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getUsers());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("Transactions")
    public ResponseEntity<ApiResponse<?>> getDeviceTransactions() {
        ApiResponse<List<DeviceDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDeviceTransactions());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("Transaction")
    public ResponseEntity<ApiResponse<?>> saveTransaction() {
        ApiResponse<List<DeviceDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDeviceTransactions());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
