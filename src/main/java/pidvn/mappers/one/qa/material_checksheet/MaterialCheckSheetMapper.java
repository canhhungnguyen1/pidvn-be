package pidvn.mappers.one.qa.material_checksheet;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.qa.material_checksheet.models.PsMaterDto;
import pidvn.modules.qa.material_checksheet.models.QaCardDto;
import pidvn.modules.qa.material_checksheet.models.SearchDto;

import java.util.List;

@Mapper
public interface MaterialCheckSheetMapper {
    List<QaCardDto> getQaCards(SearchDto searchDto);
    List<PsMaterDto> getPsMasters(String qaCard, String model);
}
