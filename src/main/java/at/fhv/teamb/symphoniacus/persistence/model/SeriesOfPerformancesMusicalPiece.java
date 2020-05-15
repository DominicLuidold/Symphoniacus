package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesMusicalPiece;
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
@Table(name = "seriesOfPerformances_musicalPiece")
public class SeriesOfPerformancesMusicalPiece implements ISeriesOfPerformancesMusicalPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seriesOfPerformances_musicalPieceId")
    private Integer seriesOfPerformancesMusicalPieceId;

    @Column(name = "musicalPieceId", insertable = false, updatable = false)
    private Integer musicalPieceId;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;

    //Many-To-One Part for MUSICALPIECE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicalPieceId")
    private IMusicalPieceEntity musicalPiece;

    @Override
    public IMusicalPieceEntity getMusicalPiece() {
        return this.musicalPiece;
    }

    @Override
    public void setMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPiece = musicalPiece;
    }

    @Override
    public Integer getSeriesOfPerformancesMusicalPieceId() {
        return this.seriesOfPerformancesMusicalPieceId;
    }

    @Override
    public void setSeriesOfPerformancesMusicalPieceId(Integer seriesOfPerformancesMusicalPieceId) {
        this.seriesOfPerformancesMusicalPieceId = seriesOfPerformancesMusicalPieceId;
    }

    @Override
    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    @Override
    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    @Override
    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    @Override
    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }
}
