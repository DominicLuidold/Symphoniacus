package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "instrumentation")
public class Instrumentation {
    @Id
    @Column(name = "instrumentationId")
    private Integer instrumentationId;

    @Column(name = "name")
    private String name;

    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;


    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }
}
