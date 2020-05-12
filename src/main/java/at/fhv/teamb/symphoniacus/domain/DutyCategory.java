package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.Collections;
import java.util.List;

/**
 * Domain object for DutyCategory.
 *
 * @author Dominic Luidold
 */
public class DutyCategory {
    private final DutyCategoryEntity entity;
    private final List<DutyCategoryChangelog> changelogList;
    private PersistenceState persistenceState;

    /**
     * Initializes the DutyCategory object based on provided {@link DutyCategoryEntity} and List
     * of {@link DutyCategoryChangelogEntity} objects.
     *
     * @param entity        The entity to use
     * @param changelogList The List of DutyCategoryChangelog to use
     */
    public DutyCategory(DutyCategoryEntity entity, List<DutyCategoryChangelog> changelogList) {
        this.entity = entity;
        this.changelogList = Collections.unmodifiableList(changelogList);
        this.persistenceState = PersistenceState.PERSISTED;
    }

    public int getPoints() {
        return this.entity.getPoints();
    }

    public PersistenceState getPersistenceState() {
        return this.persistenceState;
    }

    public void setPersistenceState(PersistenceState persistenceState) {
        this.persistenceState = persistenceState;
    }

    public DutyCategoryEntity getEntity() {
        return this.entity;
    }

    public List<DutyCategoryChangelog> getChangelogList() {
        return this.changelogList;
    }
}
