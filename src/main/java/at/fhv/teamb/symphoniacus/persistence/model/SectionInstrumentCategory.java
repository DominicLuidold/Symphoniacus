package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "section_instrumentCategory")
public class SectionInstrumentCategory implements ISectionInstrumentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_instrumentCategoryId")
    private Integer sectionInstrumentCategoryId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "sectionId")
    private Integer sectionId;

    @Override
    public Integer getSectionInstrumentCategoryId() {
        return this.sectionInstrumentCategoryId;
    }

    @Override
    public void setSectionInstrumentCategoryId(Integer sectionInstrumentCategoryId) {
        this.sectionInstrumentCategoryId = sectionInstrumentCategoryId;
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
    public Integer getSectionId() {
        return this.sectionId;
    }

    @Override
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
