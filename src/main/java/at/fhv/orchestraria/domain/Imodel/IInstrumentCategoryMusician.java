package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IInstrumentCategoryMusician {
     int getInstrumentCategoryMusicianId();
     IInstrumentCategory getInstrumentCategory();
     IMusician getMusician();
}
