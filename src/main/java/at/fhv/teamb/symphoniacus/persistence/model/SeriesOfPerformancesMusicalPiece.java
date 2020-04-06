package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "seriesOfPerformances_musicalPiece")
public class SeriesOfPerformancesMusicalPiece {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "seriesOfPerformances_musicalPieceId")
    private Integer seriesOfPerformancesMusicalPieceId;

    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;


    ////Many-To-One Part for MUSICALPIECE Table
    @ManyToOne(fetch = FetchType.LAZY)
    private MusicalPiece musicalPiece;

    public MusicalPiece getMusicalPiece() {
        return this.musicalPiece;
    }
    public void setMusicalPiece(MusicalPiece musicalPiece) {
        this.musicalPiece = musicalPiece;
    }


    //Getters and Setters
    public Integer getSeriesOfPerformancesMusicalPieceId() {
        return this.seriesOfPerformancesMusicalPieceId;
    }
    public void setSeriesOfPerformancesMusicalPieceId(Integer seriesOfPerformancesMusicalPieceId) {
        this.seriesOfPerformancesMusicalPieceId = seriesOfPerformancesMusicalPieceId;
    }

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }
}
