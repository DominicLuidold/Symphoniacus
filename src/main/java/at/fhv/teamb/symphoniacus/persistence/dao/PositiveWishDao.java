package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IPositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class PositiveWishDao extends BaseDao<IPositiveWishEntity>
    implements IPositiveWishDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> find(Integer key) {
        return this.find(PositiveWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> persist(IPositiveWishEntity elem) {
        return this.persist(PositiveWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> update(IPositiveWishEntity elem) {
        return this.update(PositiveWishEntity.class, elem);
    }

    @Override
    public boolean remove(IPositiveWishEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WishRequestable> getAllPositiveWishes(IDutyEntity duty) {
        TypedQuery<WishRequestable> query = entityManager.createQuery(
            "SELECT pw FROM PositiveWishEntity pw "
                + "JOIN pw.seriesOfPerformances sop "
                + "JOIN sop.dutyEntities de "
                + "WHERE de = :duty",
            WishRequestable.class
        );

        query.setParameter("duty", duty);

        return query.getResultList();
    }

    @Override
    public boolean hasWishRequestForGivenDutyAndMusicalPiece(IMusicianEntity musician,
                                                             IMusicalPieceEntity musicalPiece,
                                                             IDutyEntity duty) {

        TypedQuery<Long> query = entityManager
            .createQuery("SELECT COUNT(pw) FROM PositiveWishEntity pw "
                + "JOIN pw.wishEntries we "
                + "JOIN we.musicalPieces mp "
                + "WHERE we.duty = :duty AND pw.musician = :musician "
                + "AND mp =: musicalPiece", Long.class);

        query.setParameter("duty", duty);
        query.setParameter("musicalPiece", musicalPiece);
        query.setParameter("musician", musician);

        return (query.getSingleResult() >= 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IPositiveWishEntity> getAllPositiveWishesForMusician(
            IDutyEntity duty,
            IMusicianEntity musician
    ) {
        TypedQuery<PositiveWishEntity> query = entityManager
                .createQuery("SELECT pw FROM PositiveWishEntity pw "
                        + "JOIN pw.wishEntries we "
                        + "WHERE we.duty = :duty "
                        + "AND pw.musician = :musician ", PositiveWishEntity.class);

        query.setParameter("duty", duty);
        query.setParameter("musician", musician);

        return new LinkedList<>(query.getResultList());
    }

    /**
     * {@inheritDoc}
     */
    public Optional<IWishEntryEntity> getWishEntryByPositiveWish(
            IPositiveWishEntity wish,
            IDutyEntity dutyEntity
    ) {
        TypedQuery<WishEntryEntity> query = entityManager
                .createQuery("SELECT we FROM PositiveWishEntity pw "
                        + "JOIN pw.wishEntries we "
                        + "WHERE we.duty = :duty "
                        + "AND pw = :posWish ", WishEntryEntity.class);

        query.setParameter("duty", dutyEntity);
        query.setParameter("posWish", wish);
        return Optional.of(query.getSingleResult());
    }
}
