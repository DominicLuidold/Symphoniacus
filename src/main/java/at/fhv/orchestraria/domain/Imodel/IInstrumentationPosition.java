package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IInstrumentationPosition {
    int getInstrumentationPositionId();
    String getPositionDescription();
    ISectionInstrumentation getSectionInstrumentation();
    IInstrumentation getInstrumentation();

    /**
     * @return Returns unmodifiable collection of duty positions by instrumentation-position ID
     */
    Collection<IDutyPosition> getIDutyPositions();
}
