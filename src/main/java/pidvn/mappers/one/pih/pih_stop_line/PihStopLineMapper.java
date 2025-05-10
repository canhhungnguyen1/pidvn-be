package pidvn.mappers.one.pih.pih_stop_line;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.pih.pih_stop_line.models.LineVo;
import pidvn.modules.pih.pih_stop_line.models.ModelVo;
import pidvn.modules.pih.pih_stop_line.models.SearchVo;
import pidvn.modules.pih.pih_stop_line.models.StopTime;

import java.util.Date;
import java.util.List;

@Mapper
public interface PihStopLineMapper {
    List<LineVo> getLines();
    List<StopTime>getStopTimes(SearchVo searchVo);

    List<StopTime> getTotalStopTimeByShift(StopTime stopTime);
    List<ModelVo> getModels(SearchVo searchVo);

}
