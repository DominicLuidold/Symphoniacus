package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
import java.util.List;

public interface ISectionInstrumentationDao extends Dao<ISectionInstrumentationEntity> {

    /**
     * Finds all SectionInstrumentations matching the given Instrumentation.
     *
     * @param instrumentation given Instrumentation
     * @return a List of all SectionInstrumentations with the same InstrumentationId
     */
    List<ISectionInstrumentationEntity> getSectionInstrumentationToInstrumentation(
        IInstrumentationEntity instrumentation
    );
}
