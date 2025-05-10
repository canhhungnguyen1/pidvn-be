package pidvn.modules.ie.machine_document.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pidvn.entities.one.IeFileHistory;
import pidvn.entities.one.IeMachine;
import pidvn.mappers.one.ie.machine_document.IeMaDoMapper;
import pidvn.modules.ie.machine_document.models.FileInfoDto;
import pidvn.repositories.one.IeFileHistoryRepo;
import pidvn.repositories.one.IeMachineRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IeMaDoSvcImpl implements IeMaDoSvc {

    @Autowired
    private IeFileHistoryRepo ieFileHistoryRepo;

    @Autowired
    private IeMachineRepo ieMachineRepo;

    @Autowired
    private IeMaDoMapper ieMaDoMapper;

    public final String ROOT_FOLDER = "\\\\10.92.176.10\\DataSharePIDVN\\4. IE Drawing\\DRAWING-CONTROL\\1. Quản lý thiết bị\\";

    @Override
    public List<FileInfoDto> uploadFile(List<MultipartFile> files, FileInfoDto fileInfoDto) throws IOException {
        List<FileInfoDto> uploadedFiles = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    if (file != null && !file.isEmpty()) {
                        String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
                        String storedFilename = generateStoredFileName(originalFilename);

                        Path path = Paths.get(ROOT_FOLDER, storedFilename);
                        Files.write(path, file.getBytes());

                        // Gắn thông tin vào DTO
                        IeFileHistory ieFileHistory = new IeFileHistory();
                        ieFileHistory.setCreatedBy(fileInfoDto.getCreatedBy());
                        ieFileHistory.setMachineCode(fileInfoDto.getMachineCode());
                        ieFileHistory.setName(storedFilename);
                        this.ieFileHistoryRepo.save(ieFileHistory);
                    }
                } catch (IOException e) {
                    // Có thể log lỗi chi tiết cho từng file
                    e.printStackTrace();
                }
            }
        }

        return uploadedFiles;
    }

    @Override
    public List<IeMachine> getMachines() {
        return this.ieMachineRepo.findAll();
    }

    @Override
    public List<FileInfoDto> getIeMachineFiles() {
        return this.ieMaDoMapper.getIeMachineFiles();
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        String url = ROOT_FOLDER + fileName;
        return FileUtils.readFileToByteArray(new File(url));
    }

    private String generateStoredFileName(String originalFilename) {
        String cleanName = Paths.get(originalFilename).getFileName().toString();
        int dotIndex = cleanName.lastIndexOf('.');
        String nameWithoutExt = (dotIndex != -1) ? cleanName.substring(0, dotIndex) : cleanName;
        String ext = (dotIndex != -1) ? cleanName.substring(dotIndex) : "";

        String shortUuid = UUID.randomUUID().toString().substring(0, 6);

        return nameWithoutExt + "_" + shortUuid + ext;
    }


}
