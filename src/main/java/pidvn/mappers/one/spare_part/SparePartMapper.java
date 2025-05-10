package pidvn.mappers.one.spare_part;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.spare_part.models.SearchVo;
import pidvn.modules.spare_part.models.SparePartRecordVo;
import pidvn.modules.spare_part.models.SparePartRequestVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface SparePartMapper {
    List<SparePartRecordVo> getSparePartRecords(SearchVo searchVo);
    List<SparePartRecordVo> getSparePartRecordsByStandardPrice(SearchVo searchVo);
    List<SparePartRecordVo> getSparePartInventory(Integer requestId);
    List<Map<String, Object>> getSparePartIvt(String code);
    List<Map<String, Object>> getSparePartIvtHis();
    List<SparePartRequestVo> getSparePartRequestDetail(Integer requestId);

    List<SparePartRequestVo> getSparePartRequestMasters(SearchVo searchVo);
    List<SparePartRequestVo> getSparePartRequestDetailByRequestId(Integer requestId);


}
