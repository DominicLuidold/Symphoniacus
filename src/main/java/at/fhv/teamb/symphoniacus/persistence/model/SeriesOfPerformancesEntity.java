package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "seriesOfPerformances")
public class SeriesOfPerformancesEntity implements ISeriesOfPerformancesEntity {
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
    private boolean isTour;

    @ManyToMany(cascade = {CascadeType.MERGE}, targetEntity = InstrumentationEntity.class)
    @JoinTable(
        name = "seriesOfPerformances_instrumentation",
        joinColumns = {
            @JoinColumn(name = "seriesOfPerformancesId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "instrumentationId")
        }
    )
    private Set<IInstrumentationEntity> instrumentations = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE}, targetEntity = MusicalPieceEntity.class)
    @JoinTable(
        name = "seriesOfPerformances_musicalPiece",
        joinColumns = {
            @JoinColumn(name = "seriesOfPerformancesId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "musicalPieceId")
        }
    )
    private Set<IMusicalPieceEntity> musicalPieces = new LinkedHashSet<>();

    @OneToMany(mappedBy = "seriesOfPerformances", targetEntity = DutyEntity.class)
    private Set<IDutyEntity> dutyEntities;

    @OneToMany(mappedBy = "seriesOfPerformances", targetEntity = PositiveWishEntity.class)
    private List<IPositiveWishEntity> positiveWishes = new LinkedList<>();

    @OneToMany(mappedBy = "seriesOfPerformances", targetEntity = NegativeDutyWishEntity.class)
    private List<INegativeDutyWishEntity> negativeDutyWishes = new LinkedList<>();

    @OneToMany(mappedBy = "seriesOfPerformances", targetEntity = WishEntryEntity.class)
    private List<IWishEntryEntity> wishEntries = new LinkedList<>();

    @Override
    public void addPositiveWish(IPositiveWishEntity positiveWishEntity) {
        this.positiveWishes.add(positiveWishEntity);
        positiveWishEntity.setSeriesOfPerformances(this);
    }

    @Override
    public void removePositiveWish(IPositiveWishEntity positiveWishEntity) {
        this.positiveWishes.remove(positiveWishEntity);
        positiveWishEntity.setSeriesOfPerformances(null);
    }

    @Override
    public void addNegativeDutyWish(INegativeDutyWishEntity negativeDutyWishEntity) {
        this.negativeDutyWishes.add(negativeDutyWishEntity);
        negativeDutyWishEntity.setSeriesOfPerformances(this);
    }

    @Override
    public void removeNegativeDutyWish(INegativeDutyWishEntity negativeDutyWishEntity) {
        this.negativeDutyWishes.remove(negativeDutyWishEntity);
        negativeDutyWishEntity.setSeriesOfPerformances(null);
    }

    @Override
    public boolean getTour() {
        return isTour;
    }

    @Override
    public void setTour(boolean tour) {
        isTour = tour;
    }

    @Override
    public Set<IDutyEntity> getDutyEntities() {
        return dutyEntities;
    }

    @Override
    public void setDutyEntities(Set<IDutyEntity> dutyEntities) {
        this.dutyEntities = dutyEntities;
    }

    @Override
    public List<IPositiveWishEntity> getPositiveWishes() {
        return positiveWishes;
    }

    @Override
    public void setPositiveWishes(List<IPositiveWishEntity> positiveWishes) {
        this.positiveWishes = positiveWishes;
    }

    @Override
    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    @Override
    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
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
    public boolean getIsTour() {
        return this.isTour;
    }

    @Override
    public void setIsTour(boolean isTour) {
        this.isTour = isTour;
    }

    @Override
    public void addDuty(IDutyEntity dutyEntity) {
        this.dutyEntities.add(dutyEntity);
        dutyEntity.setSeriesOfPerformances(this);
    }

    @Override
    public void removeDuty(IDutyEntity dutyEntity) {
        this.dutyEntities.remove(dutyEntity);
        dutyEntity.setSeriesOfPerformances(null);
    }

    @Override
    public List<INegativeDutyWishEntity> getNegativeDutyWishes() {
        return this.negativeDutyWishes;
    }

    @Override
    public void setNegativeDutyWishes(List<INegativeDutyWishEntity> negativeDutyWishes) {
        this.negativeDutyWishes = negativeDutyWishes;
    }

    @Override
    public Set<IInstrumentationEntity> getInstrumentations() {
        return this.instrumentations;
    }

    @Override
    public void setInstrumentations(Set<IInstrumentationEntity> instrumentations) {
        this.instrumentations = instrumentations;
    }

    @Override
    public void addInstrumentation(IInstrumentationEntity instrumentationEntity) {
        this.instrumentations.add(instrumentationEntity);
        instrumentationEntity.addSeriesOfPerformance(this);
    }

    @Override
    public void removeInstrumentation(IInstrumentationEntity instrumentationEntity) {
        this.instrumentations.remove(instrumentationEntity);
        instrumentationEntity.removeSeriesOfPerformance(this);
    }

    @Override
    public Set<IMusicalPieceEntity> getMusicalPieces() {
        return this.musicalPieces;
    }

    @Override
    public void setMusicalPieces(Set<IMusicalPieceEntity> musicalPieces) {
        this.musicalPieces = musicalPieces;
    }

    @Override
    public void addMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPieces.add(musicalPiece);
        musicalPiece.addSeriesOfPerformance(this);
    }

    @Override
    public void removeMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPieces.remove(musicalPiece);
        musicalPiece.removeSeriesOfPerformance(this);
    }

    public List<IWishEntryEntity> getWishEntries() {
        return wishEntries;
    }

    @Override
    public void setWishEntries(
        List<IWishEntryEntity> wishEntries) {
        this.wishEntries = wishEntries;
    }

    @Override
    public void addWishEntry(IWishEntryEntity wishEntry) {
        this.wishEntries.add(wishEntry);
        wishEntry.setSeriesOfPerformances(this);
    }

    @Override
    public void removeWishEntry(IWishEntryEntity wishEntry) {
        this.wishEntries.remove(wishEntry);
        wishEntry.setSeriesOfPerformances(null);
    }

}
