package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryMusician;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instrumentCategory_musician")
public class InstrumentCategoryMusician implements IInstrumentCategoryMusician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentCategory_musicianId")
    private Integer instrumentCategoryMusicianId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "musicianId")
    private Integer musicianId;

    @Override
    public Integer getInstrumentCategoryMusicianId() {
        return this.instrumentCategoryMusicianId;
    }

    @Override
    public void setInstrumentCategoryMusicianId(Integer instrumentCategoryMusicianId) {
        this.instrumentCategoryMusicianId = instrumentCategoryMusicianId;
    }

    @Override
    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    @Override
    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    @Override
    public Integer getMusicianId() {
        return this.musicianId;
    }

    @Override
    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }
}
