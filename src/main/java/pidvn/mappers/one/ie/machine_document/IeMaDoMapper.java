package pidvn.mappers.one.ie.machine_document;

import pidvn.modules.ie.machine_document.models.FileInfoDto;

import java.util.List;

public interface IeMaDoMapper {
    List<FileInfoDto> getIeMachineFiles();
}
