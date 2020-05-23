package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IMusicalPiece {
     int getMusicalPieceId();
     String getName();
     String getComposer();
     String getCategory();

     /**
      * @return Returns unmodifiable collection instrumentations by musical piece ID.
      */
     Collection<IInstrumentation> getIInstrumentations();

     /**
      * @return Returns unmodifiable collection of musical pieces of series of performances by musical piece ID.
      */
     Collection<ISeriesOfPerformancesMusicalPiece> getISeriesOfPerformancesMusicalPieces();
}
