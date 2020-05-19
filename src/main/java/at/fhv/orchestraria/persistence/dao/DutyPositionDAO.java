package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyPositionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyPositionDAO implements DaoBase<DutyPositionEntity>{
    private SessionManager _sessionManager;

    public DutyPositionDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }
    @Override
    public synchronized Optional<DutyPositionEntity> get(int id) {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        Optional<DutyPositionEntity> dutyPos = Optional.ofNullable(s.get(DutyPositionEntity.class , id));
        tx.commit();
        return dutyPos;
    }

    @Override
    public synchronized List<DutyPositionEntity> getAll() {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        List<DutyPositionEntity> duties = s.createQuery("SELECT a FROM DutyPositionEntity a", DutyPositionEntity.class).getResultList();
        List<DutyPositionEntity> wrappedDuties = new ArrayList<>(duties.size());
        for(DutyPositionEntity d :duties){
            wrappedDuties.add(d);
        }
        tx.commit();
        return wrappedDuties;
    }


    @Override
    public synchronized void save(DutyPositionEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.saveOrUpdate(entity);
        tx.commit();
    }

    @Override
    public synchronized DutyPositionEntity update(DutyPositionEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        DutyPositionEntity de =  (DutyPositionEntity) s.merge(entity);
        tx.commit();
        return de;
    }

    @Override
    public synchronized void delete(DutyPositionEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.delete(entity);
        tx.commit();
    }

    /**
     *
     * @param dutypos a list of dutypositions
     * updates a list with only one connection;
     */
    public synchronized void updatelist(List<DutyPositionEntity> dutypos){
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        for (DutyPositionEntity d:dutypos) {
            DutyPositionEntity me = (DutyPositionEntity) s.merge(d);
        }
        tx.commit();
    }


    /**
     *
     * @param dutypos a list of dutypositions
     * updates a list with only one connection;
     */
    public synchronized void savelist(List<DutyPositionEntity> dutypos){
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        for (DutyPositionEntity d:dutypos) {
            s.saveOrUpdate(d);
        }
        tx.commit();
    }

}
