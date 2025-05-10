package pidvn.modules.spare_part.services;

import pidvn.entities.one.SparePartInventoryData;
import pidvn.entities.one.SparePartInventoryRequest;
import pidvn.modules.spare_part.models.SparePartIvtDto;

import java.util.List;

public interface ISparePartInventorySvc {
    List<SparePartIvtDto> getInventoryRequests();
    SparePartInventoryRequest createInventoryRequest(SparePartInventoryRequest request);
    List<SparePartInventoryData> saveInventoryData(List<SparePartInventoryData> data);
    SparePartInventoryData updateInventoryData(SparePartInventoryData obj);
    Integer deleteInventoryData(Integer id);

    List<SparePartIvtDto> getInventoryData(Integer requestId);

}
