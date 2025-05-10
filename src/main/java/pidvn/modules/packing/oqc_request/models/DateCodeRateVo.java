package pidvn.modules.packing.oqc_request.models;

public class DateCodeRateVo {
    private String dateCode;
    private Float qty;
    private Integer dateMarking;
    private Integer dateQaCard;
    private Float actualScanQty;
    private Float rate;

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
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

    public Float getActualScanQty() {
        return actualScanQty;
    }

    public void setActualScanQty(Float actualScanQty) {
        this.actualScanQty = actualScanQty;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
