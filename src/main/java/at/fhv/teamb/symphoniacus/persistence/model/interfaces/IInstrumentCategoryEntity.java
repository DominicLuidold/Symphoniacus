package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface IInstrumentCategoryEntity {

    Integer getInstrumentCategoryId();

    void setInstrumentCategoryId(Integer instrumentCategoryId);

    String getDescription();

    void setDescription(String description);

    List<IContractualObligationEntity> getContractualObligations();

    void addContractualObligation(IContractualObligationEntity contractualObligation);

    void removeContractualObligation(IContractualObligationEntity contractualObligation);

    void addMusician(IMusicianEntity musician);

    void removeMusician(IMusicianEntity musician);

    List<IMusicianEntity> getMusicians();

    void setMusicians(List<IMusicianEntity> musicians);

    void addInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition);

    void removeInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition);

}
