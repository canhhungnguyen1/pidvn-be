package pidvn.modules.pih.pih_inventory.models;

import java.util.Date;

public class InventoryRequestDto {
    private Integer id;
    private String reqNo;
    private String createdBy;
    private String remark;
    private Date inventoryCloseDate;
    private Date calculateTheoryDataDate;
    private String inventoryType;
    private String createdByName;
    private Date createdAt;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getInventoryCloseDate() {
        return inventoryCloseDate;
    }

    public void setInventoryCloseDate(Date inventoryCloseDate) {
        this.inventoryCloseDate = inventoryCloseDate;
    }

    public Date getCalculateTheoryDataDate() {
        return calculateTheoryDataDate;
    }

    public void setCalculateTheoryDataDate(Date calculateTheoryDataDate) {
        this.calculateTheoryDataDate = calculateTheoryDataDate;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
