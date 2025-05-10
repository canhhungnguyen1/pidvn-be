package pidvn.mappers.one.relay.material_control;

import org.apache.ibatis.annotations.Mapper;
import pidvn.modules.relay.material_control.models.*;
import pidvn.modules.relay.material_management.models.PurWhRecordVo;

import java.util.List;

@Mapper
public interface ReMatCtrlMapper {
    List<PurWhRecordVo> getMaterialsBySlipNo(String slipNo, String groupBy);
    List<SlipVo> getSlips();
    List<SlipVo> getSlipsRelayReturnWarehouse();
    List<MaterialVo> getMaterialHistories(String lotNo, List<String> recordTypes);
    List<MaterialVo> getMaterials(MaterialSearchVo searchVo);
    List<MaterialVo> materialTraceability(MaterialSearchVo searchVo);
    List<MaterialVo> getDataChangeQrCard(String oldQaCard);

    List<QaCardVo> getQaCard();
    List<PartVo> getPartsByModel(String model);

    List<PurWhRecordVo> getSlipRelayReturnWarehouseDetail(String slipNo, String groupBy);

    List<MaterialExport> exportMaterialData(MaterialSearchVo searchVo);

    /**
     * Lấy dữ liệu qty các lot thực tế đã sử dung trong line
     * @param lotNos danh sách các lot cần update qty
     * @return
     */
    List<MaterialVo> getActualQtyUsedInLine(List<String> lotNos);

    void updateActualQtyUsedInLine(Integer id, Float qty);

    MaterialVo getLotRequestAndLotReceive(String lotNo);
}
