package pidvn.entities.one;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "iqc_results")
@EntityListeners(AuditingEntityListener.class)
public class IqcResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "class")
    private String classParam;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "line")
    private String line;

    @Column(name = "model")
    private String model;

    @Column(name = "shift")
    private String shift;

    @Column(name = "p_date")
    private Date pDate;

    @Column(name = "date")
    private Date date;

    @Column(name = "result1")
    private String result1;

    @Column(name = "result2")
    private String result2;

    @Column(name = "result3")
    private String result3;

    @Column(name = "check_result")
    private String checkResult;

    @Column(name = "check_result2")
    private String checkResult2;

    @Column(name = "check_user")
    private String checkUser;

    @Column(name = "check_date")
    private Date checkDate;

    @Column(name = "check_remark")
    private String checkRemark;

    @Column(name = "remark")
    private String remark;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "key_in_id")
    private Integer keyInId;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "request_no")
    private String requestNo;

    @Column(name = "lot_group")
    private String lotGroup;

    @Column(name = "level_of_control_1")
    private String levelOfControl1;

    @Column(name = "level_of_control_2")
    private String levelOfControl2;

    @Column(name = "qty")
    private Float qty;

    @Column(name = "type")
    private String type;

    @Column(name = "check_sample")
    private Boolean checkSample;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassParam() {
        return classParam;
    }

    public void setClassParam(String classParam) {
        this.classParam = classParam;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getResult3() {
        return result3;
    }

    public void setResult3(String result3) {
        this.result3 = result3;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckResult2() {
        return checkResult2;
    }

    public void setCheckResult2(String checkResult2) {
        this.checkResult2 = checkResult2;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKeyInId() {
        return keyInId;
    }

    public void setKeyInId(Integer keyInId) {
        this.keyInId = keyInId;
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

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getLotGroup() {
        return lotGroup;
    }

    public void setLotGroup(String lotGroup) {
        this.lotGroup = lotGroup;
    }

    public String getLevelOfControl1() {
        return levelOfControl1;
    }

    public void setLevelOfControl1(String levelOfControl1) {
        this.levelOfControl1 = levelOfControl1;
    }

    public String getLevelOfControl2() {
        return levelOfControl2;
    }

    public void setLevelOfControl2(String levelOfControl2) {
        this.levelOfControl2 = levelOfControl2;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCheckSample() {
        return checkSample;
    }

    public void setCheckSample(Boolean checkSample) {
        this.checkSample = checkSample;
    }
}
