package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

public interface IDutyPositionEntity {
    Integer getDutyPositionId();

    void setDutyPositionId(Integer dutyPositionId);

    String getDescription();

    void setDescription(String description);

    IInstrumentationPositionEntity getInstrumentationPosition();

    void setInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition);

    IDutyEntity getDuty();

    void setDuty(IDutyEntity duty);

    ISectionEntity getSection();

    void setSection(ISectionEntity section);

    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);
}
