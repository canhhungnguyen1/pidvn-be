package pidvn.modules.packing.oqc_request.models;

public class ModelVo {
    private String model;
    private Float receiveQty;
    private Float scanQty;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(Float receiveQty) {
        this.receiveQty = receiveQty;
    }

    public Float getScanQty() {
        return scanQty;
    }

    public void setScanQty(Float scanQty) {
        this.scanQty = scanQty;
    }
}
