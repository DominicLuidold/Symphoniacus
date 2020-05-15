package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import java.util.Optional;

/**
 * Interface for DutyCategoryChangeLogDao class.
 *
 * @author Theresa Gierer
 */
public interface IDutyCategoryChangeLogDao extends Dao<IDutyCategoryChangelogEntity> {

    /**
     * Checks whether a {@link DutyCategoryChangelogEntity} exists for a {@link DutyEntity}.
     *
     * @param duty The duty to use
     * @return true if a changelog entity exists, false otherwise
     */
    boolean doesLogAlreadyExists(IDutyEntity duty);

    /**
     * Returns all {@link DutyCategoryChangelogEntity} objects based on
     * {@link DutyCategoryEntity} and start date information saved in a {@link DutyEntity}.
     *
     * @param duty The duty to use
     * @return A List of duty category changelog entities
     */
    Optional<IDutyCategoryChangelogEntity> getChangelogByDetails(IDutyEntity duty);

}
