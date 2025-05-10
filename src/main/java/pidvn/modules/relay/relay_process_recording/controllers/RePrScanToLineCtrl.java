package pidvn.modules.relay.relay_process_recording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.MaterialControls;
import pidvn.modules.relay.relay_process_recording.models.LotDto;
import pidvn.modules.relay.relay_process_recording.models.MaterialControlDto;
import pidvn.modules.relay.relay_process_recording.services.RePrScanToLineSvcImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Relay/ProcessRecording/ScanToLine")
public class RePrScanToLineCtrl {

    @Autowired
    private RePrScanToLineSvcImpl rePrScanToLineSvc;

    /**
     * Scan tem NVL
     *
     * @param lotDto
     * @return
     */
    @PostMapping("ScanMaterial")
    public ResponseEntity<ApiResponse<?>> scanMaterial(@RequestBody LotDto lotDto) {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.rePrScanToLineSvc.scanMaterial(lotDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("InsertToLine")
    public ResponseEntity<ApiResponse<?>> scanMaterial(@RequestBody List<MaterialControlDto> data) {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.rePrScanToLineSvc.insertToLine(data));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("MaterialsInsertToLine")
    public ResponseEntity<ApiResponse<?>> getMaterialsInsertToLine(@RequestParam String qaCard) {
        ApiResponse<List<MaterialControls>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.rePrScanToLineSvc.getMaterialsInsertToLine(qaCard));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
