package pidvn.modules.is.device_management.models;

import java.util.Date;

public class TransactionDto {
    private Integer id;
    private String deviceName;
    private String recordType;
    private String recordTypeName;
    private String itUserCode;
    private String itUserName;
    private String picCode;
    private String picName;
    private Date date;
    private String sectionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    public String getItUserCode() {
        return itUserCode;
    }

    public void setItUserCode(String itUserCode) {
        this.itUserCode = itUserCode;
    }

    public String getItUserName() {
        return itUserName;
    }

    public void setItUserName(String itUserName) {
        this.itUserName = itUserName;
    }

    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
