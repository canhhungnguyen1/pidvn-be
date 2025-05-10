package pidvn.modules.qa.icp_data.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IcpData;
import pidvn.entities.one.Model;
import pidvn.entities.one.PsMaster;
import pidvn.modules.qa.icp_data.models.IcpDataDto;
import pidvn.modules.qa.icp_data.models.SearchDto;
import pidvn.modules.qa.icp_data.services.IcpDataSvcImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("QA/ICP")
public class IcpDataCtrl {

    @Autowired
    private IcpDataSvcImpl icpDataSvc;

    @GetMapping("Models")
    public ResponseEntity<ApiResponse<?>> getModels() {
        ApiResponse<List<Model>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.getModel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("IcpData")
    public ResponseEntity<ApiResponse<?>> getIcpData(@RequestParam String parentModel) {
        ApiResponse<List<IcpDataDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.getIcpData(parentModel));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("PsMasters")
    public ResponseEntity<ApiResponse<?>> getPsMasters(@RequestParam String parentModel) {
        ApiResponse<List<PsMaster>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.getPsMasters(parentModel));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("IcpData")
    public ResponseEntity<ApiResponse<?>> insertIcpData(@RequestBody IcpDataDto icpDataDto) {
        ApiResponse<IcpData> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.insertIcpData(icpDataDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("IcpData")
    public ResponseEntity<ApiResponse<?>> updateIcpData(@RequestBody IcpDataDto icpDataDto) throws Exception {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.updateIcpData(icpDataDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("IcpData/{id}")
    public ResponseEntity<ApiResponse<?>> deleteIcpData(@PathVariable Integer id) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.deleteIcpData(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping(value = "Preview")
    public ResponseEntity<?> previewTestReportFile(@RequestBody SearchDto searchDto) {

        String ROOT_FOLDER = this.icpDataSvc.ROOT_FOLDER;

        String path = ROOT_FOLDER + searchDto.getTestNo() + ".pdf";

        File file = new File(path);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileContent = IOUtils.toByteArray(fileInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setResult(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("Download")
    public ResponseEntity<byte[]> downloadTestReportFile(@RequestBody SearchDto searchDto) {
        byte[] file = new byte[0];

        String ROOT_FOLDER = this.icpDataSvc.ROOT_FOLDER;
        String path = ROOT_FOLDER + searchDto.getTestNo() + ".pdf";

        try {
            file = FileUtils.readFileToByteArray(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(file);
    }

    @PostMapping("TestReport")
    public ResponseEntity<ApiResponse<?>> uploadTestReport(
            @RequestParam("files") MultipartFile[] files) {

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.icpDataSvc.uploadTestReports(files));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
