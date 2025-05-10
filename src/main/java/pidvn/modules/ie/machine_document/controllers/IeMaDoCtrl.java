package pidvn.modules.ie.machine_document.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.IeFileHistory;
import pidvn.entities.one.IeMachine;
import pidvn.modules.ie.machine_document.models.FileInfoDto;
import pidvn.modules.ie.machine_document.services.IeMaDoSvc;
import pidvn.modules.ie.machine_document.services.IeMaDoSvcImpl;
import reactor.util.annotation.Nullable;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("IE/Machine/")
public class IeMaDoCtrl {

    @Autowired
    private IeMaDoSvcImpl ieMaDoSvc;

    @GetMapping("Files")
    public ResponseEntity<?> getIeMachineFiles() {
        ApiResponse<List<FileInfoDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.ieMaDoSvc.getIeMachineFiles());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("Machines")
    public ResponseEntity<?> getMachines() {
        ApiResponse<List<IeMachine>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.ieMaDoSvc.getMachines());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("File")
    public ResponseEntity<?> uploadFile(
            @RequestPart("files") @Nullable List<MultipartFile> files,
            @RequestPart("fileInfo") FileInfoDto fileInfo) throws IOException {
        ApiResponse<List<FileInfoDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.ieMaDoSvc.uploadFile(files, fileInfo));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("DownloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestBody FileInfoDto fileInfoDto) throws IOException {
        return ResponseEntity.ok().body(this.ieMaDoSvc.downloadFile(fileInfoDto.getName()));
    }

}
