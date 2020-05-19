package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISectionInstrumentation {
     int getSectionInstrumentationId();
     String getPredefinedSectionInstrumentation();
     IInstrumentation getInstrumentation();
     ISection getSection();

     /**
      * @return Returns unmodifiable collection of instrumentation positions by section instrumentation ID
      */
     Collection<IInstrumentationPosition> getIInstrumentationPositions();
}
