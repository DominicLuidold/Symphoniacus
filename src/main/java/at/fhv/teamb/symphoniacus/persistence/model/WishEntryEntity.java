package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WishEntry")
public class WishEntryEntity implements IWishEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishEntryId")
    private Integer wishEntryId;

    @ManyToOne(fetch = FetchType.LAZY,
        targetEntity = SeriesOfPerformancesEntity.class,
        optional = true)
    @JoinColumn(name = "seriesOfPerformancesId")
    private ISeriesOfPerformancesEntity seriesOfPerformances;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY,
        targetEntity = PositiveWishEntity.class,
        optional = true)
    @JoinColumn(name = "positiveWishId")
    private IPositiveWishEntity positiveWish;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY,
        targetEntity = NegativeDutyWishEntity.class,
        optional = true)
    @JoinColumn(name = "negativeWishId")
    private INegativeDutyWishEntity negativeDutyWish;

    @ManyToOne(fetch = FetchType.LAZY,
        targetEntity = DutyEntity.class,
        optional = true)
    @JoinColumn(name = "dutyId")
    private IDutyEntity duty;

    @ManyToMany(cascade = {CascadeType.MERGE,
        CascadeType.PERSIST}, targetEntity = MusicalPieceEntity.class)
    @JoinTable(
        name = "WishEntry_MusicalPieces",
        joinColumns = {
            @JoinColumn(name = "wishEntryId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "musicalPieceId")
        }
    )
    private List<IMusicalPieceEntity> musicalPieces = new LinkedList<>();


    @Override
    public Integer getWishEntryId() {
        return this.wishEntryId;
    }

    @Override
    public void setWishEntryId(Integer id) {
        this.wishEntryId = id;
    }

    @Override
    public ISeriesOfPerformancesEntity getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(ISeriesOfPerformancesEntity series) {
        this.seriesOfPerformances = series;
    }

    @Override
    public IDutyEntity getDuty() {
        return this.duty;
    }

    @Override
    public void setDuty(IDutyEntity duty) {
        this.duty = duty;
    }

    @Override
    public IPositiveWishEntity getPositiveWish() {
        return this.positiveWish;
    }

    @Override
    public void setPositiveWish(IPositiveWishEntity positiveWish) {
        this.positiveWish = positiveWish;
    }

    @Override
    public INegativeDutyWishEntity getNegativeDutyWish() {
        return this.negativeDutyWish;
    }

    @Override
    public void setNegativeDutyWish(INegativeDutyWishEntity negativeDutyWish) {
        this.negativeDutyWish = negativeDutyWish;
    }

    @Override
    public void addMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPieces.add(musicalPiece);
        musicalPiece.getWishEntries().add(this);
    }

    @Override
    public void removeMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPieces.remove(musicalPiece);
        musicalPiece.getWishEntries().remove(this);
    }

    public List<IMusicalPieceEntity> getMusicalPieces() {
        return this.musicalPieces;
    }

    public void setMusicalPieces(List<IMusicalPieceEntity> entityList) {
        this.musicalPieces = entityList;
    }
}
