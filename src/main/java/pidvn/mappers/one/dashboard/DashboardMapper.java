package pidvn.mappers.one.dashboard;

import pidvn.modules.dashboard.models.RelayQtyVo;

import java.util.List;

public interface DashboardMapper {
    List<RelayQtyVo> getManufacturingQuantity();
}
