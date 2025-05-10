package pidvn.mappers.one.spare_part;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.spare_part.models.SparePartIvtDto;

import java.util.List;

@Mapper
public interface SparePartInventoryMapper {

    List<SparePartIvtDto> getInventoryRequests();
    List<SparePartIvtDto> getInventoryData(Integer requestId);

}
