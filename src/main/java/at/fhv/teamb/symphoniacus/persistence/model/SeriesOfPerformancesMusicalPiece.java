package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "seriesOfPerfomances_musicalPiece")
public class SeriesOfPerfomancesMusicalPiece {
    @Id
    @Column(name = "seriesOfPerfomances_musicalPieceId")
    private Integer seriesOfPerfomancesMusicalPieceId;

    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

    @Column(name = "seriesOfPerfomancesId")
    private Integer seriesOfPerfomancesId;


    public Integer getSeriesOfPerfomancesMusicalPieceId() {
        return this.seriesOfPerfomancesMusicalPieceId;
    }

    public void setSeriesOfPerfomancesMusicalPieceId(Integer seriesOfPerfomancesMusicalPieceId) {
        this.seriesOfPerfomancesMusicalPieceId = seriesOfPerfomancesMusicalPieceId;
    }

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    public Integer getSeriesOfPerfomancesId() {
        return this.seriesOfPerfomancesId;
    }

    public void setSeriesOfPerfomancesId(Integer seriesOfPerfomancesId) {
        this.seriesOfPerfomancesId = seriesOfPerfomancesId;
    }
}
