package pidvn.modules.qa.material_checksheet.services;

import pidvn.entities.one.QaMaterialCheckSheetConfirm;
import pidvn.entities.one.QaMaterialCheckSheetRecord;
import pidvn.modules.qa.material_checksheet.models.PsMaterDto;
import pidvn.modules.qa.material_checksheet.models.QaCardDto;
import pidvn.modules.qa.material_checksheet.models.SearchDto;

import java.util.List;
import java.util.Map;

public interface MaterialCheckSheetSvc {

    List<QaCardDto> getQaCards(SearchDto searchDto);
    List<PsMaterDto> getPsMasters(String qaCard);
    Map<String, Object> confirmCheckSheet(QaMaterialCheckSheetConfirm object);
    Map<String, Object> saveCheckSheetRecord(QaMaterialCheckSheetRecord object);
    List<QaMaterialCheckSheetRecord> getCheckSheetRecords(String qaCard);
    Integer deleteCheckSheetRecord(Integer id);




}
