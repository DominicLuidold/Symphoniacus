package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import java.util.List;

public interface IInstrumentCategoryDao {
    List<IInstrumentCategoryEntity> getAll();
}
