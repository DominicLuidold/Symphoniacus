package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "seriesOfPerformances")
public class SeriesOfPerformances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "isTour")
    private Boolean isTour;

    @Column(name = "instrumentationId")
    private Integer instrumentationId;

    @OneToMany(mappedBy = "seriesOfPerformances", orphanRemoval = false)
    private Set<DutyEntity> dutyEntities;

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getIsTour() {
        return this.isTour;
    }

    public void setIsTour(Boolean isTour) {
        this.isTour = isTour;
    }

    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    public void addDuty(DutyEntity dutyEntity) {
        this.dutyEntities.add(dutyEntity);
        dutyEntity.setSeriesOfPerformances(this);
    }

    public void removeDuty(DutyEntity dutyEntity) {
        this.dutyEntities.remove(dutyEntity);
        dutyEntity.setSeriesOfPerformances(null);
    }
}
