package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDutyWishDao extends BaseDao<INegativeDutyWishEntity>
    implements INegativeDutyWishDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> find(Integer key) {
        return this.find(NegativeDutyWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> persist(INegativeDutyWishEntity elem) {
        return this.persist(NegativeDutyWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> update(INegativeDutyWishEntity elem) {
        return this.update(NegativeDutyWishEntity.class, elem);
    }

    @Override
    public boolean remove(INegativeDutyWishEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WishRequestable> getAllNegativeDutyWishes(IDutyEntity duty) {
        TypedQuery<WishRequestable> query = entityManager.createQuery(
            "SELECT nw FROM NegativeDutyWishEntity nw "
                + "JOIN nw.seriesOfPerformances sop "
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
            .createQuery("SELECT COUNT(nw) FROM NegativeDutyWishEntity nw "
                + "JOIN nw.wishEntries we "
                + "JOIN we.musicalPieces mp "
                + "WHERE we.duty = :duty "
                + "AND nw.musician = :musician "
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
    public List<INegativeDutyWishEntity> getAllNegativeDutyWishesForMusician(
            IDutyEntity duty,
            IMusicianEntity musician
    ) {
        TypedQuery<NegativeDutyWishEntity> query = entityManager
                .createQuery("SELECT nw FROM NegativeDutyWishEntity nw "
                        + "JOIN nw.wishEntries we "
                        + "WHERE we.duty = :duty "
                        + "AND nw.musician = :musician ", NegativeDutyWishEntity.class);

        query.setParameter("duty", duty);
        query.setParameter("musician", musician);

        return new LinkedList<>(query.getResultList());

    }
}
