package pidvn.modules.relay.relay_process_recording.models;

import java.util.Date;

public class MaterialControlDto {

   private String ppn ;
   private String cpn ;
   private String line ;
   private Date date ;
   private String shift ;
   private String plotno ;
   private String clotno ;
   private Float qty ;
   private String frBox ;
   private String user1 ;
   private String user2;
   private String keyUser ;
   private Integer ngQty ;
   private String recordType ;
   private Integer processId ;
   private Integer ordinal;
   private String remark;

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getCpn() {
        return cpn;
    }

    public void setCpn(String cpn) {
        this.cpn = cpn;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getClotno() {
        return clotno;
    }

    public void setClotno(String clotno) {
        this.clotno = clotno;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public String getFrBox() {
        return frBox;
    }

    public void setFrBox(String frBox) {
        this.frBox = frBox;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public Integer getNgQty() {
        return ngQty;
    }

    public void setNgQty(Integer ngQty) {
        this.ngQty = ngQty;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
