package at.fhv.orchestraria.domain.model;

import at.fhv.orchestraria.domain.Imodel.IVacation;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * @author generated by Intellij -  edited by Team C
 */

/*
 * Generated by IntelliJ
 */

@Entity
@Table(name = "vacation", schema = "ni128610_1sql8")
public class VacationEntityC implements IVacation, Serializable {
    private int vacationId;
    private Date startDate;
    private Date endDate;
    private boolean isConfirmed;
    private MusicianEntityC musician;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacationId")
    @Override
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @Basic
    @Column(name = "startDate")
    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "endDate")
    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "isConfirmed")
    @Override
    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationEntityC that = (VacationEntityC) o;
        return vacationId == that.vacationId &&
                isConfirmed == that.isConfirmed &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacationId, startDate, endDate, isConfirmed);
    }

    @ManyToOne
    @JoinColumn(name = "musicianId", referencedColumnName = "musicianId", nullable = false)
    @Override
    public MusicianEntityC getMusician() {
        return musician;
    }

    public void setMusician(MusicianEntityC musician) {
        this.musician = musician;
    }
}
