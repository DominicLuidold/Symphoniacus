package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyPositionEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyPositionDAO implements DaoBase<DutyPositionEntityC>{
    private SessionManager _sessionManager;

    public DutyPositionDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }
    @Override
    public synchronized Optional<DutyPositionEntityC> get(int id) {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        Optional<DutyPositionEntityC> dutyPos = Optional.ofNullable(s.get(DutyPositionEntityC.class , id));
        tx.commit();
        return dutyPos;
    }

    @Override
    public synchronized List<DutyPositionEntityC> getAll() {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        List<DutyPositionEntityC> duties = s.createQuery("SELECT a FROM DutyPositionEntity a", DutyPositionEntityC.class).getResultList();
        List<DutyPositionEntityC> wrappedDuties = new ArrayList<>(duties.size());
        for(DutyPositionEntityC d :duties){
            wrappedDuties.add(d);
        }
        tx.commit();
        return wrappedDuties;
    }


    @Override
    public synchronized void save(DutyPositionEntityC entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.saveOrUpdate(entity);
        tx.commit();
    }

    @Override
    public synchronized DutyPositionEntityC update(DutyPositionEntityC entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        DutyPositionEntityC de =  (DutyPositionEntityC) s.merge(entity);
        tx.commit();
        return de;
    }

    @Override
    public synchronized void delete(DutyPositionEntityC entity) {
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
    public synchronized void updatelist(List<DutyPositionEntityC> dutypos){
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        for (DutyPositionEntityC d:dutypos) {
            DutyPositionEntityC me = (DutyPositionEntityC) s.merge(d);
        }
        tx.commit();
    }


    /**
     *
     * @param dutypos a list of dutypositions
     * updates a list with only one connection;
     */
    public synchronized void savelist(List<DutyPositionEntityC> dutypos){
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        for (DutyPositionEntityC d:dutypos) {
            s.saveOrUpdate(d);
        }
        tx.commit();
    }

}
