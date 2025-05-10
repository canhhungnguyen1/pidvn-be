package pidvn.modules.qa.material_checksheet.models;

import java.util.Date;
import java.util.List;

public class SearchDto {

    private List<Date> dateRange;

    public List<Date> getDateRange() {
        return dateRange;
    }

    public void setDateRange(List<Date> dateRange) {
        this.dateRange = dateRange;
    }
}
