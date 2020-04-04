package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "seriesOfPerfomances")
public class SeriesOfPerfomances {
    @Id
    @Column(name = "seriesOfPerfomancesId")
    private Integer seriesOfPerfomancesId;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "endDate")
    private java.sql.Date endDate;

    @Column(name = "isTournee")
    private null isTournee;

    @Column(name = "instrumentationId")
    private Integer instrumentationId;


    public Integer getSeriesOfPerfomancesId() {
        return this.seriesOfPerfomancesId;
    }

    public void setSeriesOfPerfomancesId(Integer seriesOfPerfomancesId) {
        this.seriesOfPerfomancesId = seriesOfPerfomancesId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public null getIsTournee() {
        return this.isTournee;
    }

    public void setIsTournee(null isTournee) {
        this.isTournee = isTournee;
    }

    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }
}
