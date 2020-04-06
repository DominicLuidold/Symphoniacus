package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "instrumentCategory")
public class InstrumentCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "description")
    private String description;


    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
