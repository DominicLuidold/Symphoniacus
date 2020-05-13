package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;

/**
 * Interface for points in a musician table model.
 *
 * @author Valentin Goronjic
 */
public interface MusicianPointsTableModel {
    String getBalancePoints();

    String getDebitPoints();

    String getGainedPoints();

    Musician getMusician();
}
