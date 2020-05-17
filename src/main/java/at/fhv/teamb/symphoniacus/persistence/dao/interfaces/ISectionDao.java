package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.List;

public interface ISectionDao extends Dao<ISectionEntity> {

    /**
     * Returns all {@link SectionEntity} objects.
     *
     * @return A List of sections
     */
    List<ISectionEntity> getAll();
}
