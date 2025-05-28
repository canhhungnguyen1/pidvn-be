package pidvn.modules.packing.oqc_request.services;

import pidvn.entities.one.OqcRequest;
import pidvn.modules.packing.oqc_request.models.DataSummaryVo;
import pidvn.modules.packing.oqc_request.models.OqcRequestVo;
import pidvn.modules.packing.oqc_request.models.SearchVo;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPackingOqcRequest {
    Map createOqcRequest(OqcRequestVo oqcRequestVo) throws MessagingException, UnsupportedEncodingException;

    OqcRequest updateOqcRequest(OqcRequestVo oqcRequestVo);

    List<DataSummaryVo> summaryData(SearchVo searchVo);

    public Map<String, Object> systemValidate(String qaCard);
}
