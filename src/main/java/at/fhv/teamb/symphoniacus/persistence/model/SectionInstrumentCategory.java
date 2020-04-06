package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "section_instrumentCategory")
public class SectionInstrumentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_instrumentCategoryId")
    private Integer sectionInstrumentCategoryId;

    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "sectionId")
    private Integer sectionId;

    public Integer getSectionInstrumentCategoryId() {
        return this.sectionInstrumentCategoryId;
    }

    public void setSectionInstrumentCategoryId(Integer sectionInstrumentCategoryId) {
        this.sectionInstrumentCategoryId = sectionInstrumentCategoryId;
    }

    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
