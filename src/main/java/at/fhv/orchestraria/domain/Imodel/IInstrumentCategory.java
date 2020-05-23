package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IInstrumentCategory {
     int getInstrumentCategoryId();
     String getDescription();

     /**
      * @return Returns unmodifiable collection of contractual obligations by instrument-category ID
      */
     Collection<IContractualObligation> getIContractualObligations();

     /**
      * @return Returns unmodifiable collection of instrument category musicians by instrument-category ID.
      */
     Collection<IInstrumentCategoryMusician> getIInstrumentCategoryMusicians();

     /**
      * @return Returns unmodifiable collection of section-instrument-categories by instrument-category ID
      */
     Collection<ISectionInstrumentCategory> getISectionInstrumentCategories();
}
