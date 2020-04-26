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
@Table(name = "instrumentCategory")
public class InstrumentCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "instrumentCategory")
    private List<ContractualObligationEntity> contractualObligations = new LinkedList<>();

    // TODO - @ManyToMany InstrumentationCategory - InstrumentationCategory_Musician - Musician

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

    public List<ContractualObligationEntity> getContractualObligations() {
        return this.contractualObligations;
    }

    public void addContractualObligation(ContractualObligationEntity contractualObligation) {
        this.contractualObligations.add(contractualObligation);
        contractualObligation.setInstrumentCategory(this);
    }

    public void removeContractualObligation(ContractualObligationEntity contractualObligation) {
        this.contractualObligations.remove(contractualObligation);
        contractualObligation.setInstrumentCategory(null);
    }
}
