package pidvn.modules.spare_part.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.SparePartInventoryData;
import pidvn.entities.one.SparePartInventoryRequest;
import pidvn.modules.spare_part.models.SparePartIvtDto;
import pidvn.modules.spare_part.services.SparePartInventorySvc;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("SparePartInventory")
public class SparePartInventoryCtrl {

    @Autowired
    private SparePartInventorySvc sparePartInventorySvc;


    @GetMapping("Requests")
    public ResponseEntity<ApiResponse<?>> getInventoryRequests() {
        ApiResponse<List<SparePartIvtDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.getInventoryRequests());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("Request")
    public ResponseEntity<ApiResponse<?>> createInventoryRequest(@RequestBody SparePartInventoryRequest request) {
        ApiResponse<SparePartInventoryRequest> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.createInventoryRequest(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("InventoryData")
    public ResponseEntity<ApiResponse<?>> getInventoryData(@RequestParam Integer requestId) {
        ApiResponse<List<SparePartIvtDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.getInventoryData(requestId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("InventoryData")
    public ResponseEntity<ApiResponse<?>> saveInventoryData(@RequestBody List<SparePartInventoryData> data) {
        ApiResponse<List<SparePartInventoryData>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.saveInventoryData(data));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("InventoryData")
    public ResponseEntity<ApiResponse<?>> updateInventoryData(@RequestBody SparePartInventoryData obj) {
        ApiResponse<SparePartInventoryData> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.updateInventoryData(obj));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("InventoryData/{id}")
    public ResponseEntity<ApiResponse<?>> deleteInventoryData(@PathVariable Integer id) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.sparePartInventorySvc.deleteInventoryData(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
