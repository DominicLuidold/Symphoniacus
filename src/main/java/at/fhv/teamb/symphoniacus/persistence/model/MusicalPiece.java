package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "musicalPiece")
public class MusicalPiece {
    @Id
    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

    @Column(name = "name")
    private String name;

    @Column(name = "composer")
    private String composer;

    @Column(name = "category")
    private String category;


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
