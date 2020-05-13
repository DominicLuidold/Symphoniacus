package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;

/**
 * @author Valentin
 */
public interface TableModel {
    Integer getBalancePoints();
    Integer getDebitPoints();
    Integer getGainedPoints();
    Musician getMusician();
}
