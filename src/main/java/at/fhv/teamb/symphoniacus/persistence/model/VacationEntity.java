package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IVacationEntity;
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
@Table(name = "vacation")
public class VacationEntity implements IVacationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacationId")
    private Integer vacationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicianId")
    private MusicianEntity musician;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "isConfirmed")
    private boolean isConfirmed;

    @Override
    public Integer getVacationId() {
        return this.vacationId;
    }

    @Override
    public void setVacationId(Integer vacationId) {
        this.vacationId = vacationId;
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
    public boolean getIsConfirmed() {
        return this.isConfirmed;
    }

    @Override
    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
