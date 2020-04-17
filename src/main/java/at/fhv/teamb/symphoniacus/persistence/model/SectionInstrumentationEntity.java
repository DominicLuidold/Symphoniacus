package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sectionInstrumentation")
public class SectionInstrumentationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionInstrumentationId")
    private Integer sectionInstrumentationId;

    // TODO - Missing:
    //  - sectionId
    //  - Constraint `fk_sectionInstrumentation_section`
    //    - FOREIGN KEY (`sectionId`)
    //    - REFERENCES `ni128610_1sql2`.`section` (`sectionId`)

    @Column(name = "instrumentationId")
    private Integer instrumentationId;

    public Integer getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    public void setSectionInstrumentationId(Integer sectionInstrumentationId) {
        this.sectionInstrumentationId = sectionInstrumentationId;
    }

    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }
}
