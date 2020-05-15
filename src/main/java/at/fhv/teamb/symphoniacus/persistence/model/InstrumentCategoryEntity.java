package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
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
public class InstrumentCategoryEntity implements IInstrumentCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentCategoryId")
    private Integer instrumentCategoryId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "instrumentCategory")
    private List<IContractualObligationEntity> contractualObligations = new LinkedList<>();

    // TODO - @ManyToMany InstrumentationCategory - InstrumentationCategory_Musician - Musician

    @Override
    public Integer getInstrumentCategoryId() {
        return this.instrumentCategoryId;
    }

    @Override
    public void setInstrumentCategoryId(Integer instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<IContractualObligationEntity> getContractualObligations() {
        return this.contractualObligations;
    }

    @Override
    public void addContractualObligation(IContractualObligationEntity contractualObligation) {
        this.contractualObligations.add(contractualObligation);
        contractualObligation.setInstrumentCategory(this);
    }

    @Override
    public void removeContractualObligation(IContractualObligationEntity contractualObligation) {
        this.contractualObligations.remove(contractualObligation);
        contractualObligation.setInstrumentCategory(null);
    }
}
