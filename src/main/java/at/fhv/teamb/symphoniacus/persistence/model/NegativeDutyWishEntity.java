package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
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
@Table(name = "negativeDutyWish")
public class NegativeDutyWishEntity implements INegativeDutyWishEntity, WishRequestable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negativeDutyId")
    private Integer negativeDutyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicianId")
    private MusicianEntity musician;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seriesOfPerformancesId")
    private SeriesOfPerformancesEntity seriesOfPerformances;

    @Override
    public Integer getNegativeDutyId() {
        return this.negativeDutyId;
    }

    @Override
    public void setNegativeDutyId(Integer negativeDutyId) {
        this.negativeDutyId = negativeDutyId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MusicianEntity getMusician() {
        return musician;
    }

    @Override
    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }

    @Override
    public SeriesOfPerformancesEntity getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(
        SeriesOfPerformancesEntity seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    @Override // TODO - Create own domain object and outsource this
    public LocalDate getStartDate() {
        return null;
    }

    @Override // TODO - Create own domain object and outsource this
    public LocalDate getEndDate() {
        return null;
    }
}
