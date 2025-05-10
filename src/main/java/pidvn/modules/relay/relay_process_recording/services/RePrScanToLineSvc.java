package pidvn.modules.relay.relay_process_recording.services;

import pidvn.entities.one.MaterialControls;
import pidvn.modules.relay.relay_process_recording.models.LotDto;
import pidvn.modules.relay.relay_process_recording.models.MaterialControlDto;

import java.util.List;
import java.util.Map;

public interface RePrScanToLineSvc {
    Map<String, Object> scanMaterial(LotDto lotDto);
    Map<String, Object> insertToLine(List<MaterialControlDto> dataInsert);
    List<MaterialControls> getMaterialsInsertToLine(String qaCard);
}
