package at.fhv.orchestraria.domain.Imodel;

import java.time.LocalDate;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IContractualObligation {
     int getContractNr();
     String getPosition();
     int getPointsPerMonth();
     LocalDate getStartDate();
     LocalDate getEndDate();
     IMusician getMusician();
     IInstrumentCategory getInstrumentCategory();
}
