package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IDutyPosition {
    int getDutyPositionId();
    String getDescription();
    IInstrumentationPosition getInstrumentationPosition();
    IDuty getDuty();
    ISection getSection();
    IMusician getMusician();
}
