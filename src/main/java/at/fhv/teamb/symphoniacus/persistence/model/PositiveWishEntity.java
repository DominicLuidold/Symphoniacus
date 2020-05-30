package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "positiveWish")
public class PositiveWishEntity implements IPositiveWishEntity, WishRequestable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "positiveWishId")
    private Integer positiveWishId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicianEntity.class)
    @JoinColumn(name = "musicianId")
    private IMusicianEntity musician;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SeriesOfPerformancesEntity.class)
    @JoinColumn(name = "seriesOfPerformancesId")
    private ISeriesOfPerformancesEntity seriesOfPerformances;

    @OneToMany(mappedBy = "positiveWish", targetEntity = WishEntryEntity.class)
    private List<IWishEntryEntity> wishEntries = new LinkedList<>();

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

    @Override
    public Integer getPositiveWishId() {
        return this.positiveWishId;
    }

    @Override
    public void setPositiveWishId(Integer positiveWishId) {
        this.positiveWishId = positiveWishId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override // TODO - Create own domain object and outsource this
    public LocalDate getStartDate() {
        return null;
    }

    @Override // TODO - Create own domain object and outsource this
    public LocalDate getEndDate() {
        return null;
    }

    @Override
    public Integer getID() {
        return this.positiveWishId;
    }

    public List<IWishEntryEntity> getWishEntries() {
        return wishEntries;
    }

    public void setWishEntries(
        List<IWishEntryEntity> wishEntries) {
        this.wishEntries = wishEntries;
    }

    @Override
    public void addWishEntry(IWishEntryEntity wishEntry) {
        this.wishEntries.add(wishEntry);
        wishEntry.setPositiveWish(this);
    }

    @Override
    public void removeWishEntry(IWishEntryEntity wishEntry) {
        this.wishEntries.remove(wishEntry);
        wishEntry.setPositiveWish(null);
    }
}
