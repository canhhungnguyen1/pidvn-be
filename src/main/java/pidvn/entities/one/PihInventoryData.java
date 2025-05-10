package pidvn.entities.one;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pih_inventory_data")
@EntityListeners(AuditingEntityListener.class)
public class PihInventoryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "qty")
    private Float qty;

    @Column(name = "inventory_area")
    private Integer inventoryArea;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "record_type")
    private String recordType;

    @Transient
    private String outerLotNo;

    @Column(name = "goods_type")
    private String goodsType;

    @Column(name = "material_truck")
    private String materialTruck;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Integer getInventoryArea() {
        return inventoryArea;
    }

    public void setInventoryArea(Integer inventoryArea) {
        this.inventoryArea = inventoryArea;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getOuterLotNo() {
        return outerLotNo;
    }

    public void setOuterLotNo(String outerLotNo) {
        this.outerLotNo = outerLotNo;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getMaterialTruck() {
        return materialTruck;
    }

    public void setMaterialTruck(String materialTruck) {
        this.materialTruck = materialTruck;
    }
}
