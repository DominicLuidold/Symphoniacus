package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicianEntity.class)
    @JoinColumn(name = "musicianId")
    private IMusicianEntity musician;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SeriesOfPerformancesEntity.class)
    @JoinColumn(name = "seriesOfPerformancesId")
    private ISeriesOfPerformancesEntity seriesOfPerformances;

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
    public IMusicianEntity getMusician() {
        return musician;
    }

    @Override
    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
    }

    @Override
    public ISeriesOfPerformancesEntity getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(ISeriesOfPerformancesEntity seriesOfPerformances) {
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
