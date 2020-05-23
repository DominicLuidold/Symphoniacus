package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISectionInstrumentCategory {
     int getSectionInstrumentCategoryId();
     IInstrumentCategory getInstrumentCategory();
     ISection getSection();
}
