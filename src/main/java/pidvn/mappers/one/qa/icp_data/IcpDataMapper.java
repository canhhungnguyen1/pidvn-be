package pidvn.mappers.one.qa.icp_data;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.qa.icp_data.models.IcpDataDto;

import java.util.List;

@Mapper
public interface IcpDataMapper {
    List<IcpDataDto> getIcpData(String parentModel);
}
