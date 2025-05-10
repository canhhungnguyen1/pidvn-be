package pidvn.mappers.one.relay.relay_process_recording;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.relay.relay_process_recording.models.LotDto;

@Mapper
public interface RePrScanToLineMapper {
    LotDto getLotRequestAndLotReceive(String lotNo);
}
