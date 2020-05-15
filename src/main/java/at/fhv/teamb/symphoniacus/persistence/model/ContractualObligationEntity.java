package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
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
public class ContractualObligationEntity implements IContractualObligationEntity {
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
    private IInstrumentCategoryEntity instrumentCategory;

    @Override
    public Integer getContractNr() {
        return this.contractNr;
    }

    @Override
    public void setContractNr(Integer contractNr) {
        this.contractNr = contractNr;
    }

    @Override
    public String getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public Integer getPointsPerMonth() {
        return this.pointsPerMonth;
    }

    @Override
    public void setPointsPerMonth(Integer pointsPerMonth) {
        this.pointsPerMonth = pointsPerMonth;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public MusicianEntity getMusician() {
        return this.musician;
    }

    @Override
    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }

    @Override
    public IInstrumentCategoryEntity getInstrumentCategory() {
        return this.instrumentCategory;
    }

    @Override
    public void setInstrumentCategory(
        IInstrumentCategoryEntity instrumentCategory
    ) {
        this.instrumentCategory = instrumentCategory;
    }
}
