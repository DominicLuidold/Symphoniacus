package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "musicalPiece")
public class MusicalPieceEntity implements IMusicalPieceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

    @Column(name = "name")
    private String name;

    @Column(name = "composer")
    private String composer;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "musicalPiece", targetEntity = InstrumentationEntity.class)
    private Set<IInstrumentationEntity> instrumentations = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "musicalPieces", targetEntity = SeriesOfPerformancesEntity.class)
    private List<ISeriesOfPerformancesEntity> seriesOfPerformances = new LinkedList<>();

    @ManyToMany(mappedBy = "musicalPieces", targetEntity = WishEntryEntity.class)
    private List<IWishEntryEntity> wishEntries = new LinkedList<>();

    @Override
    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    @Override
    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getComposer() {
        return this.composer;
    }

    @Override
    public void setComposer(String composer) {
        this.composer = composer;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
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
    public void addInstrumentation(IInstrumentationEntity instrumentation) {
        this.instrumentations.add(instrumentation);
        instrumentation.setMusicalPiece(this);
    }

    @Override
    public void removeInstrumentation(IInstrumentationEntity instrumentation) {
        this.instrumentations.remove(instrumentation);
        instrumentation.setMusicalPiece(null);
    }

    @Override
    public List<ISeriesOfPerformancesEntity> getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(List<ISeriesOfPerformancesEntity> seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    @Override
    public void addSeriesOfPerformance(ISeriesOfPerformancesEntity seriesOfPerformancesEntity) {
        this.seriesOfPerformances.add(seriesOfPerformancesEntity);
        seriesOfPerformancesEntity.addMusicalPiece(this);
    }

    @Override
    public void removeSeriesOfPerformance(ISeriesOfPerformancesEntity seriesOfPerformancesEntity) {
        this.seriesOfPerformances.remove(seriesOfPerformancesEntity);
        seriesOfPerformancesEntity.removeMusicalPiece(this);
    }

    @Override
    public List<IWishEntryEntity> getWishEntries() {
        return this.wishEntries;
    }

    @Override
    public void setWishEntries(List<IWishEntryEntity> wishEntries) {
        this.wishEntries = wishEntries;
    }

    @Override
    public void addWishEntry(IWishEntryEntity wishEntryEntity) {
        this.wishEntries.add(wishEntryEntity);
        wishEntryEntity.getMusicalPieces().add(this);
    }

    @Override
    public void removeWishEntry(IWishEntryEntity wishEntryEntity) {
        this.wishEntries.remove(wishEntryEntity);
        wishEntryEntity.removeMusicalPiece(this);
    }
}
