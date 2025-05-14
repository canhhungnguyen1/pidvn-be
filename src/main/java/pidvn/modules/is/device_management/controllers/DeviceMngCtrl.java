package pidvn.modules.is.device_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IsDevice;
import pidvn.entities.one.IsDeviceLocation;
import pidvn.modules.is.device_management.models.*;
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
     * PI: IS/DeviceManagement/Locations
     * @return
     */
    @GetMapping("Locations")
    public ResponseEntity<ApiResponse<?>> getLocations() {
        ApiResponse<List<IsDeviceLocation>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getLocations());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

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
     * API: IS/DeviceManagement/Devices/{deviceName}
     * @param deviceName
     * @return
     * @throws Exception
     */
    @GetMapping("Devices/{deviceName}")
    public ResponseEntity<ApiResponse<?>> getDevice(@PathVariable String deviceName) throws Exception {
        ApiResponse<DeviceDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getDevice(deviceName));
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
        ApiResponse<List<InventoryRequestDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getInventoryRequests());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IS/DeviceManagement/Inventory/Request
     *
     * @param ivtReqDto
     * @return
     */
    @PostMapping("Inventory/Request")
    public ResponseEntity<ApiResponse<?>> createInventoryRequest(@RequestBody InventoryRequestDto ivtReqDto) {
        ApiResponse<InventoryRequestDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.createInventoryRequest(ivtReqDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    /**
     * API: IS/DeviceManagement/Inventory/Data
     * @return
     */
    @GetMapping("Inventory/Data")
    public ResponseEntity<ApiResponse<?>> getInventoryData(@RequestParam Integer requestId) {
        ApiResponse<List<InventoryDataDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.getInventoryData(requestId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("Inventory/Data")
    public ResponseEntity<ApiResponse<?>> saveInventoryData(@RequestBody InventoryDataDto ivtDatDto) {
        ApiResponse<InventoryDataDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.deviceMngSvc.saveInventoryData(ivtDatDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
