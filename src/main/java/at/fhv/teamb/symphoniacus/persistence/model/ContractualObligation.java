
import javax.persistence.*;

@Entity
@Table(name = "contractualObligation")
public class ContractualObligation {
    @Id
    @Column(name = "contractNr")
    private Integer contractNr;

    @Column(name = "position")
    private String position;

    @Column(name = "PointsPerMonth")
    private Integer pointsPerMonth;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "endDate")
    private java.sql.Date endDate;

    @Column(name = "musicanId")
    private Integer musicanId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;


    public Integer getContractNr() {
        return this.contractNr;
    }

    public void setContractNr(Integer contractNr) {
        this.contractNr = contractNr;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getPointsPerMonth() {
        return this.pointsPerMonth;
    }

    public void setPointsPerMonth(Integer pointsPerMonth) {
        this.pointsPerMonth = pointsPerMonth;
    }

    public java.sql.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }

    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }
}
