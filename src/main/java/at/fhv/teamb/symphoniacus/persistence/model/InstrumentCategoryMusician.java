package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "instrumentCategory_musician")
public class InstrumentCategoryMusician {
    @Id
    @Column(name = "instrumentCategory_musicanId")
    private Integer instrumentCategoryMusicanId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "muscianId")
    private Integer muscianId;


    public Integer getInstrumentCategoryMusicanId() {
        return this.instrumentCategoryMusicanId;
    }

    public void setInstrumentCategoryMusicanId(Integer instrumentCategoryMusicanId) {
        this.instrumentCategoryMusicanId = instrumentCategoryMusicanId;
    }

    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public Integer getMuscianId() {
        return this.muscianId;
    }

    public void setMuscianId(Integer muscianId) {
        this.muscianId = muscianId;
    }
}
