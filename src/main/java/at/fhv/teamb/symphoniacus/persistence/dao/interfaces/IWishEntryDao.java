package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;

import java.util.List;
import java.util.Optional;

public interface IWishEntryDao extends Dao<IWishEntryEntity> {

    Optional<IWishEntryEntity> find(Integer key);

    Optional<IWishEntryEntity> persist(IWishEntryEntity elem);

    Optional<IWishEntryEntity> update(IWishEntryEntity elem);

    List<IWishEntryEntity> findAll();

    boolean remove(IWishEntryEntity elem);
}
