package pidvn.modules.ie.machine_document.services;

import org.springframework.web.multipart.MultipartFile;
import pidvn.entities.one.IeMachine;
import pidvn.modules.ie.machine_document.models.FileInfoDto;

import java.io.IOException;
import java.util.List;

public interface IeMaDoSvc {
    List<FileInfoDto> uploadFile(List<MultipartFile> files, FileInfoDto fileInfoDto) throws IOException;
    List<IeMachine> getMachines();

    List<FileInfoDto> getIeMachineFiles();

    byte[] downloadFile(String fileName) throws IOException;
}
