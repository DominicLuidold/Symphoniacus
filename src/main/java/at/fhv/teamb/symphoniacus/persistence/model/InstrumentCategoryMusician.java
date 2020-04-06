package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "instrumentCategory_musician")
public class InstrumentCategoryMusician {
    @Id
    @Column(name = "instrumentCategory_musicianId")
    private Integer instrumentCategoryMusicianId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "musicianId")
    private Integer musicianId;


    public Integer getInstrumentCategoryMusicianId() {
        return this.instrumentCategoryMusicianId;
    }

    public void setInstrumentCategoryMusicianId(Integer instrumentCategoryMusicianId) {
        this.instrumentCategoryMusicianId = instrumentCategoryMusicianId;
    }

    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }
}
