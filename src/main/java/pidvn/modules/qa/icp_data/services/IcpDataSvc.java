package pidvn.modules.qa.icp_data.services;

import org.springframework.web.multipart.MultipartFile;
import pidvn.entities.one.IcpData;
import pidvn.entities.one.Model;
import pidvn.entities.one.PsMaster;
import pidvn.modules.qa.icp_data.models.IcpDataDto;

import java.util.List;
import java.util.Map;

public interface IcpDataSvc {
    List<Model> getModel();
    List<IcpDataDto> getIcpData(String parentModel);
    List<PsMaster> getPsMasters(String parentModel);

    IcpData insertIcpData(IcpDataDto icpDataDto);
    Map<String, Object> updateIcpData(IcpDataDto icpDataDto) throws Exception;
    Integer deleteIcpData(Integer id);
    Map<String, Object> uploadTestReports(MultipartFile[] files);

}
