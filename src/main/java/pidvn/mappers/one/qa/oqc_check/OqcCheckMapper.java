package pidvn.mappers.one.qa.oqc_check;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.qa.oqc_check.models.*;

import java.util.List;

@Mapper
public interface OqcCheckMapper {
    List<OqcDataVo> getOqcMasterData(String reqNo, String qaCard);
    List<OqcRequestVo> getOqcRequests(SearchVo searchVo);
    List<PsMasterVo> getPsMasterData(String qaCard);
    List<DateCodeVo> getDateCode(String qaCard);
}
