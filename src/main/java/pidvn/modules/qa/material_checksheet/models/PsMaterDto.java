package pidvn.modules.qa.material_checksheet.models;

import java.util.Date;

public class PsMaterDto {
    private String pncomp;
    private String ppn;
    private String cpn;
    private String line;
    private Date date;
    private String shift;
    private String plotno;
    private String clotno;
    private Float qty;
    private Date createdAt;
    private Date updatedAt;
    private String processName;

    public String getPncomp() {
        return pncomp;
    }

    public void setPncomp(String pncomp) {
        this.pncomp = pncomp;
    }

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

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
