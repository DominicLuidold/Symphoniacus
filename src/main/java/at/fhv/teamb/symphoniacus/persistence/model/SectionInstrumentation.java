package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "sectionInstrumentation")
public class SectionInstrumentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionInstrumentationId")
    private Integer sectionInstrumentationId;

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
