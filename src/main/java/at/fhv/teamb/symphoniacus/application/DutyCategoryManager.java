package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.domain.DutyCategoryChangelog;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyCategoryManager {
    private static final Logger LOG = LogManager.getLogger(DutyCategoryManager.class);
    private final DutyCategoryDao categoryDao;
    private List<DutyCategory> dutyCategories;
    private List<DutyCategoryEntity> dutyCategoryEntities;

    public DutyCategoryManager() {
        this.categoryDao = new DutyCategoryDao();
    }

    /**
     * Returns all {@link DutyCategory} objects.
     *
     * @return A List of duty category objects
     */
    public List<DutyCategory> getDutyCategories() {
        // Fetch duty categories from database if not present
        if (this.dutyCategories == null) {
            // Get duty category entities from database
            this.dutyCategories = new LinkedList<>();
            if (this.dutyCategoryEntities == null) {
                this.dutyCategoryEntities = this.categoryDao.getAll();
            }

            // Convert duty category entities to domain objects
            for (DutyCategoryEntity entity : this.dutyCategoryEntities) {
                // Convert duty category changelog entities to domain objects
                List<DutyCategoryChangelog> changelogList = new LinkedList<>();
                for (DutyCategoryChangelogEntity changelogEntity :
                    entity.getDutyCategoryChangelogs()
                ) {
                    changelogList.add(new DutyCategoryChangelog(changelogEntity));
                }

                // Create domain object
                this.dutyCategories.add(new DutyCategory(entity, changelogList));
            }
        }
        return this.dutyCategories;
    }

    /**
     * Updates the points of a duty category.
     *
     * <p>This method <b>WILL NOT</b> persist the changes made to the object. To persist any
     * changes made, refer to {@link #persist(DutyCategory)}. It will, however, subsequently
     * change the {@link PersistenceState} of the object from {@link PersistenceState#PERSISTED}
     * to {@link PersistenceState#EDITED}, provided that given {@code points} value is valid.
     *
     * @param dutyCategory The duty category to use
     * @param points       The new value of points to use
     * @see #persist(DutyCategory)
     */
    public void updatePoints(DutyCategory dutyCategory, int points) {
        if (!dutyCategory.newPointsValid(points)) {
            return;
        }

        // Update object state
        dutyCategory.setPersistenceState(PersistenceState.EDITED);

        // Delegate to domain object
        dutyCategory.updatePoints(points);
    }

    /**
     * Persists the changes made to the {@link DutyCategory} object to the database.
     *
     * <p>The method will subsequently change the {@link PersistenceState} of the object
     * from {@link PersistenceState#EDITED} to {@link PersistenceState#PERSISTED}, provided
     * that the database update was successful.
     *
     * @param dutyCategory The duty category to persist
     */
    public void persist(DutyCategory dutyCategory) {
        Optional<DutyCategoryEntity> persisted = this.categoryDao.update(dutyCategory.getEntity());

        if (persisted.isPresent()) {
            dutyCategory.setPersistenceState(PersistenceState.PERSISTED);
            LOG.debug(
                "Persisted duty category {{}, '{}'}",
                dutyCategory.getEntity().getDutyCategoryId(),
                dutyCategory.getEntity().getType()
            );
        } else {
            LOG.error(
                "Could not persist duty category {{}, '{}'}",
                dutyCategory.getEntity().getDutyCategoryId(),
                dutyCategory.getEntity().getType()
            );
        }
    }
}
