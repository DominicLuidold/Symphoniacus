package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.util.List;
import java.util.Optional;

public interface IDutyCategoryDao extends Dao<IDutyCategoryEntity> {

    /**
     * Returns all duty categories.
     *
     * @return A List of duty categories
     */
    List<IDutyCategoryEntity> getAll();

    /**
     * searches all dutyCategories for a given name.
     *
     * @param type given name of a dutyCategories
     * @return the musical piece with the same name
     */
    Optional<IDutyCategoryEntity> getDutyCategoryFromName(String type);
}
