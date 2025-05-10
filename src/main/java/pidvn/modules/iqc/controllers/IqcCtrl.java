package pidvn.modules.iqc.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IqcLevelOfControl;
import pidvn.modules.iqc.models.*;
import pidvn.modules.iqc.services.IqcSvcImpl;
import pidvn.modules.iqc.utils.IqcExporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("IQC")
public class IqcCtrl {

    @Autowired
    private IqcSvcImpl iqcSvc;

    @PostMapping("IqcRequests")
    public ResponseEntity<ApiResponse<?>> getIqcRequests(@RequestBody SearchDto searchDto) {
        ApiResponse<List<IqcRequestDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getIqcRequests(searchDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("IqcRequest/{requestNo}")
    public ResponseEntity<ApiResponse<?>> getIqcRequest(@PathVariable String requestNo) {
        ApiResponse<IqcRequestDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getIqcRequest(requestNo));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("IqcRequest")
    public ResponseEntity<ApiResponse<?>> createIqcRequest(@RequestBody IqcRequestDto iqcRequestDto) throws Exception {
        ApiResponse<Map> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.createIqcRequest(iqcRequestDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("IqcRequest")
    public ResponseEntity<ApiResponse<?>> updateIqcRequest(@RequestBody IqcRequestDto iqcRequestDto) {
        ApiResponse<IqcRequestDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.updateIqcRequest(iqcRequestDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("SlipNo")
    public ResponseEntity<ApiResponse<?>> getSlipNo() {
        ApiResponse<List<PurWhRecordDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getSlipNo());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("IqcResults")
    public ResponseEntity<ApiResponse<?>> getIqcResults(@RequestParam String requestNo) {
        ApiResponse<List<IqcResultDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getIqcResults(requestNo));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("IqcLevelOfControls")
    public ResponseEntity<ApiResponse<?>> getIqcLevelOfControls() {
        ApiResponse<List<IqcLevelOfControl>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getIqcLevelOfControls());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("HistoryLevelOfControls")
    public ResponseEntity<ApiResponse<?>> getHistoryLevelOfControls(@RequestParam String model) {
        ApiResponse<List<IqcResultDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getHistoryLevelOfControls(model));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("EvaluateLotNos")
    public ResponseEntity<ApiResponse<?>> evaluateLotNos(@RequestBody List<IqcResultDto> iqcResults) {
        ApiResponse<List<IqcResultDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.evaluateLotNos(iqcResults));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: IQC/InventoryLots
     * @return
     */
    @GetMapping("InventoryLots")
    public ResponseEntity<ApiResponse<?>> getLotsInventory() {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.getLotsInventory());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    /**
     * API: IQC/PrepareDataCreateRequest
     * Chuẩn bị data để tạo rquest IQC
     * @param searchDto
     * @return
     */
    @PostMapping("PrepareDataCreateRequest")
    public ResponseEntity<ApiResponse<?>> prepareDataCreateRequest(@RequestBody SearchDto searchDto) {
        ApiResponse<List<PihStoreDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.iqcSvc.prepareDataCreateRequest(searchDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("ExportExcel")
    public ResponseEntity<?> exportExcel(@RequestBody IqcRequestDto iqcRequestDto, @RequestParam String type) throws IOException {

        if (type.equals("export")) {
            List<IqcResultDto> data = this.iqcSvc.getIqcResultsExportExcelOrderByModel(iqcRequestDto.getRequestNo());
            IqcExporter exporter = new IqcExporter(data);
            ByteArrayInputStream inputStream = exporter.export();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IqcData.xlsx");
            return ResponseEntity.ok().headers(headers).body(new InputStreamResource(inputStream));
        }else if (type.equals("group")) {
            ApiResponse<List<IqcResultDto>> apiResponse = new ApiResponse<>();
            apiResponse.setResult(this.iqcSvc.getIqcResultsExportExcel(iqcRequestDto.getRequestNo()));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        return null;
    }

    /**
     * API: IQC/ViewGuide
     * @return
     */
    @PostMapping("ViewGuide")
    public Object viewGuide() {
        String url = "P:\\IS\\CanhHung\\Project\\IQC Check\\IQC.pdf";

        File file = new File(url);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Đánh dấu lot là mẫu kiểm tra
     * @param id
     * @return
     */
    @PatchMapping("IqcResult/{id}/SetCheckSample")
    public ResponseEntity<?> setCheckSample(@PathVariable Integer id, @RequestBody Map<String, Boolean> data) {
        ApiResponse<IqcResultDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(iqcSvc.setCheckSample(id, data.get("checkSample")));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
