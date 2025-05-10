package pidvn.entities.one;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pidvn_tooljig_inventory")
@EntityListeners(AuditingEntityListener.class)
public class SparePartIvt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "rack_type")
    private String rack_type;

    @JsonProperty("current")
    @Column(name = "qty")
    private int qty;

    @Column(name = "ivt_dif")
    private String ivt_dif;

    @Column(name = "ivt_no")
    private String ivt_no;

    @Column(name = "username")
    private String username;

    @Column(name = "date_time")
    private String date_time;
    @Column(name = "updated_at")
    private String updated_at;


    public SparePartIvt() {
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIvt_dif() {
        return ivt_dif;
    }

    public void setIvt_dif(String ivt_dif) {
        this.ivt_dif = ivt_dif;
    }

    public String getIvt_no() {
        return ivt_no;
    }

    public void setIvt_no(String ivt_no) {
        this.ivt_no = ivt_no;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRack_type() {
        return rack_type;
    }

    public void setRack_type(String rack_type) {
        this.rack_type = rack_type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
