package pidvn.modules.qa.oqc_check.models;

public class DateCodeVo {
    private String dateCode;
    private Integer qty;
    private Integer dateMarking;
    private Integer dateQaCard;
    private Integer actualScanQty;
    private Float rate;

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDateMarking() {
        return dateMarking;
    }

    public void setDateMarking(Integer dateMarking) {
        this.dateMarking = dateMarking;
    }

    public Integer getDateQaCard() {
        return dateQaCard;
    }

    public void setDateQaCard(Integer dateQaCard) {
        this.dateQaCard = dateQaCard;
    }

    public Integer getActualScanQty() {
        return actualScanQty;
    }

    public void setActualScanQty(Integer actualScanQty) {
        this.actualScanQty = actualScanQty;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
