package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contractualObligation")
public class ContractualObligation {
    @Id
    @Column(name = "contractNr")
    private Integer _contractNr;

    @Column(name = "position")
    private String _position;

    @Column(name = "PointsPerMonth")
    private Integer _pointsPerMonth;

    @Column(name = "startDate")
    private LocalDate _startDate;

    @Column(name = "endDate")
    private LocalDate _endDate;

    @Column(name = "musicanId")
    private Integer _musicanId;

    @Column(name = "instrumentCategoryId")
    private Integer _instrumentCategoryId;


    public Integer getContractNr() {
        return _contractNr;
    }

    public void setContractNr(Integer contractNr) {
        _contractNr = contractNr;
    }

    public String getPosition() {
        return _position;
    }

    public void setPosition(String position) {
        _position = position;
    }

    public Integer getPointsPerMonth() {
        return _pointsPerMonth;
    }

    public void setPointsPerMonth(Integer pointsPerMonth) {
        _pointsPerMonth = pointsPerMonth;
    }

    public LocalDate getStartDate() {
        return _startDate;
    }

    public void setStartDate(LocalDate startDate) {
        _startDate = startDate;
    }

    public LocalDate getEndDate() {
        return _endDate;
    }

    public void setEndDate(LocalDate endDate) {
        _endDate = endDate;
    }

    public Integer getMusicanId() {
        return _musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        _musicanId = musicanId;
    }

    public Integer getInstrumentCategoryId() {
        return _instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        _instrumentCategoryId = instrumentCategoryId;
    }
}
