package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import java.util.List;
import java.util.Optional;

public interface IDutyCategoryChangeLogDao extends Dao<IDutyCategoryChangelogEntity> {

    /**
     * Finds all {@link DutyCategoryChangelogEntity} matching that belong to a given
     * {@link DutyCategoryEntity}.
     *
     * @param categoryEntity The duty category to use
     * @return A List of DutyCategoryChangelogEntity objects
     */
    List<IDutyCategoryChangelogEntity> getDutyCategoryChangelogs(
        IDutyCategoryEntity categoryEntity
    );

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
