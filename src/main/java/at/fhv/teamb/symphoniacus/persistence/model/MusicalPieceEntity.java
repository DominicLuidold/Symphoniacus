package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "musicalPiece")
public class MusicalPieceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

    @Column(name = "name")
    private String name;

    @Column(name = "composer")
    private String composer;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "musicalPiece", orphanRemoval = true)
    private List<InstrumentationEntity> instrumentations = new LinkedList<>();

    // TODO - @ManyToMany zw. MusicalPiece & SeriesOfPerformances fehlt

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

    public List<InstrumentationEntity> getInstrumentations() {
        return this.instrumentations;
    }

    public void addInstrumentation(InstrumentationEntity instrumentation) {
        this.instrumentations.add(instrumentation);
        instrumentation.setMusicalPiece(this);
    }

    public void removeInstrumentation(InstrumentationEntity instrumentation) {
        this.instrumentations.remove(instrumentation);
        instrumentation.setMusicalPiece(null);
    }
}
