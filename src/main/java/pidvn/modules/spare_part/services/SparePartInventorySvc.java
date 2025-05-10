package pidvn.modules.spare_part.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pidvn.entities.one.SparePartInventoryData;
import pidvn.entities.one.SparePartInventoryRequest;
import pidvn.mappers.one.spare_part.SparePartInventoryMapper;
import pidvn.modules.spare_part.models.SparePartIvtDto;
import pidvn.repositories.one.SparePartInventoryDataRepo;
import pidvn.repositories.one.SparePartInventoryRequestRepo;

import java.util.List;

@Service
public class SparePartInventorySvc implements ISparePartInventorySvc {

    @Autowired
    private SparePartInventoryRequestRepo sparePartIvtReqRepo;

    @Autowired
    private SparePartInventoryDataRepo sparePartIvtDataRepo;

    @Autowired
    private SparePartInventoryMapper sparePartIvtMapper;

    @Override
    public List<SparePartIvtDto> getInventoryRequests() {
        return this.sparePartIvtMapper.getInventoryRequests();
    }

    @Override
    public SparePartInventoryRequest createInventoryRequest(SparePartInventoryRequest request) {
        return this.sparePartIvtReqRepo.save(request);
    }

    @Override
    public List<SparePartInventoryData> saveInventoryData(List<SparePartInventoryData> data) {
        return this.sparePartIvtDataRepo.saveAll(data);
    }

    @Override
    public SparePartInventoryData updateInventoryData(SparePartInventoryData obj) {
        return this.sparePartIvtDataRepo.save(obj);
    }

    @Override
    public Integer deleteInventoryData(Integer id) {
        this.sparePartIvtDataRepo.deleteById(id);
        return id ;
    }

    @Override
    public List<SparePartIvtDto> getInventoryData(Integer requestId) {
        return this.sparePartIvtMapper.getInventoryData(requestId);
    }
}
