package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import java.util.List;

public interface IInstrumentCategoryDao extends Dao<IInstrumentCategoryEntity> {
    List<IInstrumentCategoryEntity> getAll();
}
