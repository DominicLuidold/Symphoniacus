package at.fhv.orchestraria.domain.integrationInterfaces;

import at.fhv.orchestraria.domain.Imodel.ISeriesOfPerformances;

import java.time.LocalDateTime;

public interface IntegratableDuty {
    boolean isRehearsal();

    IntegratableSeriesOfPerformances getSeriesOfPerformances();

    String getDutyCategoryDescription();

    String getDescription();

    String getMusicalPieceString();

    String getComposerString();

    LocalDateTime getStart();

    LocalDateTime getEnd();

    String getInstrumentationString();

    int getDutyId();
}
