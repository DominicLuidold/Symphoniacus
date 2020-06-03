package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IWishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class WishEntryDao extends BaseDao<IWishEntryEntity> implements IWishEntryDao {
    /**
     * Finds the object based on the provided primary key.
     *
     * @param key The primary key to use
     * @return The object
     */
    @Override
    public Optional<IWishEntryEntity> find(Integer key) {
        return this.find(WishEntryEntity.class, key);
    }

    /**
     * Persists an object.
     *
     * @param elem The object to persist
     * @return Optional.empty if persisting not possible
     */
    @Override
    public Optional<IWishEntryEntity> persist(IWishEntryEntity elem) {
        return this.persist(WishEntryEntity.class, elem);
    }

    /**
     * Updates an existing object.
     *
     * @param elem The object to update
     * @return Optional.empty if updating not possible
     */
    @Override
    public Optional<IWishEntryEntity> update(IWishEntryEntity elem) {
        return this.update(WishEntryEntity.class, elem);
    }

    @Override
    public boolean remove(IWishEntryEntity elem) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(elem);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * Preloading all WishEntries.
     * //@param duty given duty
     *
     * @return list of wishentries
     */
    public List<IWishEntryEntity> findAll() {
        TypedQuery<WishEntryEntity> query = entityManager
            .createQuery("SELECT we FROM WishEntryEntity we",
                WishEntryEntity.class);

        //query.setParameter("duty", duty);
        return new LinkedList<>(query.getResultList());
    }
}
