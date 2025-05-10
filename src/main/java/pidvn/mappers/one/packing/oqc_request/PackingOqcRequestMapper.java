package pidvn.mappers.one.packing.oqc_request;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.packing.oqc_request.models.DataSummaryVo;
import pidvn.modules.packing.oqc_request.models.DateCodeRateVo;
import pidvn.modules.packing.oqc_request.models.ModelVo;
import pidvn.modules.packing.oqc_request.models.SearchVo;

import java.util.List;

@Mapper
public interface PackingOqcRequestMapper {
    List<DataSummaryVo> summaryData(SearchVo searchVo);

    List<DateCodeRateVo> getDateCodeRate(String qcCard);

    List<ModelVo> getDataScan(String qaCard);
}
