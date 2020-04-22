package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contractualObligation")
public class ContractualObligationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractNr")
    private Integer contractNr;

    @Column(name = "position")
    private String position;

    @Column(name = "PointsPerMonth")
    private Integer pointsPerMonth;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicianId")
    private MusicianEntity musician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumentCategoryId")
    private InstrumentCategoryEntity instrumentCategory;

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

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MusicianEntity getMusician() {
        return this.musician;
    }

    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }

    public InstrumentCategoryEntity getInstrumentCategory() {
        return this.instrumentCategory;
    }

    public void setInstrumentCategory(
        InstrumentCategoryEntity instrumentCategory
    ) {
        this.instrumentCategory = instrumentCategory;
    }
}
