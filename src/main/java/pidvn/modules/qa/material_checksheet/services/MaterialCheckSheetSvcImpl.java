package pidvn.modules.qa.material_checksheet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pidvn.entities.one.QaMaterialCheckSheetConfirm;
import pidvn.entities.one.QaMaterialCheckSheetRecord;
import pidvn.mappers.one.qa.material_checksheet.MaterialCheckSheetMapper;
import pidvn.modules.qa.material_checksheet.models.PsMaterDto;
import pidvn.modules.qa.material_checksheet.models.QaCardDto;
import pidvn.modules.qa.material_checksheet.models.SearchDto;
import pidvn.repositories.one.QaMaterialCheckSheetConfirmRepo;
import pidvn.repositories.one.QaMaterialCheckSheetRecordRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialCheckSheetSvcImpl implements MaterialCheckSheetSvc {

    @Autowired
    private QaMaterialCheckSheetConfirmRepo qaMaterialCheckSheetConfirmRepo;

    @Autowired
    private MaterialCheckSheetMapper materialCheckSheetMapper;

    @Autowired
    private QaMaterialCheckSheetRecordRepo qaMaterialCheckSheetRecordRepo;


    @Override
    public List<QaCardDto> getQaCards(SearchDto searchDto) {
        return this.materialCheckSheetMapper.getQaCards(searchDto);
    }

    @Override
    public List<PsMaterDto> getPsMasters(String qaCard) {
        String model = qaCard.split("\\*")[0];
        return this.materialCheckSheetMapper.getPsMasters(qaCard, model);
    }

    @Override
    public Map<String, Object> confirmCheckSheet(QaMaterialCheckSheetConfirm object) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", this.qaMaterialCheckSheetConfirmRepo.save(object));
        return result;
    }

    @Override
    public Map<String, Object> saveCheckSheetRecord(QaMaterialCheckSheetRecord object) {
        QaMaterialCheckSheetRecord response = this.qaMaterialCheckSheetRecordRepo.save(object);
        List<QaMaterialCheckSheetRecord> data = this.qaMaterialCheckSheetRecordRepo.findByQaCardOrderByIdDesc(object.getQaCard());

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("saved", response);

        return result;
    }

    @Override
    public List<QaMaterialCheckSheetRecord> getCheckSheetRecords(String qaCard) {
        return this.qaMaterialCheckSheetRecordRepo.findByQaCardOrderByIdDesc(qaCard);
    }

    @Override
    public Integer deleteCheckSheetRecord(Integer id) {
        this.qaMaterialCheckSheetRecordRepo.deleteById(id);
        return id;
    }


}
