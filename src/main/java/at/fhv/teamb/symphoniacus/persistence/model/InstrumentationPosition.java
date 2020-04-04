package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "instrumentationPosition")
public class InstrumentationPosition {
    @Id
    @Column(name = "instrumentationPositionId")
    private Integer instrumentationPositionId;

    @Column(name = "sectionInstrumentationId")
    private Integer sectionInstrumentationId;

    @Column(name = "instumentaionId")
    private Integer instumentaionId;

    @Column(name = "position")
    private String position;


    public Integer getInstrumentationPositionId() {
        return this.instrumentationPositionId;
    }

    public void setInstrumentationPositionId(Integer instrumentationPositionId) {
        this.instrumentationPositionId = instrumentationPositionId;
    }

    public Integer getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    public void setSectionInstrumentationId(Integer sectionInstrumentationId) {
        this.sectionInstrumentationId = sectionInstrumentationId;
    }

    public Integer getInstumentaionId() {
        return this.instumentaionId;
    }

    public void setInstumentaionId(Integer instumentaionId) {
        this.instumentaionId = instumentaionId;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
