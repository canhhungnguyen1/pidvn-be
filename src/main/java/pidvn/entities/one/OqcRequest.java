package pidvn.entities.one;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "oqc_request")
@EntityListeners(AuditingEntityListener.class)
public class OqcRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "req_no")
    private String reqNo;

    @Column(name = "qa_card")
    private String qaCard;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "oqc_request_status")
    private Integer oqcRequestStatus;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "oqc_date")
    private Date oqcDate;

    @Column(name = "judgment")
    private String judgment;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "sorting_qty")
    private Float sortingQty;

    @Column(name = "remark")
    private String remark;

    @Column(name = "flag")
    private Integer flag;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "is_special_request")
    private Integer isSpecialRequest;

    @Column(name = "accepted_by")
    private String acceptedBy;

    @Column(name = "special_remark")
    private String specialRemark;

    @Column(name = "accepted_result")
    private String acceptedResult;


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

    public String getQaCard() {
        return qaCard;
    }

    public void setQaCard(String qaCard) {
        this.qaCard = qaCard;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getOqcRequestStatus() {
        return oqcRequestStatus;
    }

    public void setOqcRequestStatus(Integer oqcRequestStatus) {
        this.oqcRequestStatus = oqcRequestStatus;
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

    public Date getOqcDate() {
        return oqcDate;
    }

    public void setOqcDate(Date oqcDate) {
        this.oqcDate = oqcDate;
    }

    public String getJudgment() {
        return judgment;
    }

    public void setJudgment(String judgment) {
        this.judgment = judgment;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Float getSortingQty() {
        return sortingQty;
    }

    public void setSortingQty(Float sortingQty) {
        this.sortingQty = sortingQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getIsSpecialRequest() {
        return isSpecialRequest;
    }

    public void setIsSpecialRequest(Integer isSpecialRequest) {
        this.isSpecialRequest = isSpecialRequest;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark;
    }

    public String getAcceptedResult() {
        return acceptedResult;
    }

    public void setAcceptedResult(String acceptedResult) {
        this.acceptedResult = acceptedResult;
    }

    @Override
    public String toString() {
        return "OqcRequest{" +
                "id=" + id +
                ", reqNo='" + reqNo + '\'' +
                ", qaCard='" + qaCard + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", oqcRequestStatus=" + oqcRequestStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", oqcDate=" + oqcDate +
                ", judgment='" + judgment + '\'' +
                ", priority=" + priority +
                ", sortingQty=" + sortingQty +
                ", remark='" + remark + '\'' +
                ", flag=" + flag +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
