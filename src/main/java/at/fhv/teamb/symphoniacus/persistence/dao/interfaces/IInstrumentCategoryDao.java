package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import java.util.List;
import java.util.Optional;

public interface IInstrumentCategoryDao {
    List<IInstrumentCategoryEntity> getAll();

    Optional<IInstrumentCategoryEntity> find(Integer key);

    Optional<IInstrumentCategoryEntity> persist(IInstrumentCategoryEntity elem);

    Optional<IInstrumentCategoryEntity> update(IInstrumentCategoryEntity elem);

    boolean remove(IInstrumentCategoryEntity elem);
}
