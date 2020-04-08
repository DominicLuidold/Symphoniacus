package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "musicalPiece")
public class MusicalPiece {
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

    //One-To-Many Part for SERIESOFPERFORMANCESMUSICALPIECE Table
    @OneToMany(mappedBy = "musicalPiece", orphanRemoval = true)
    private Set<SeriesOfPerformancesMusicalPiece> seriesOfPerformancesMusicalPieceSet =
        new HashSet<>();

    public Set<SeriesOfPerformancesMusicalPiece> getSeriesOfPerformancesMusicalPiece() {
        return this.seriesOfPerformancesMusicalPieceSet;
    }

    public void setSeriesOfPerformancesMusicalPieceSet(
        Set<SeriesOfPerformancesMusicalPiece> seriesOfPerformancesMusicalPieceSet) {
        this.seriesOfPerformancesMusicalPieceSet = seriesOfPerformancesMusicalPieceSet;
    }

    public void addSeriesOfPerformancesMusicalPiece(
        SeriesOfPerformancesMusicalPiece seriesOfPerformancesMusicalPiece) {
        this.seriesOfPerformancesMusicalPieceSet.add(seriesOfPerformancesMusicalPiece);
        seriesOfPerformancesMusicalPiece.setMusicalPiece(this);
    }

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return this.composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
