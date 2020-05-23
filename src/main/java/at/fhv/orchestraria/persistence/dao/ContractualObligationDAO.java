package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.ContractualObligationEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class ContractualObligationDAO implements DaoBase<ContractualObligationEntity> {

    private SessionManager _sessionManager;

    public ContractualObligationDAO(SessionManager sessionManager){
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<ContractualObligationEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<ContractualObligationEntity> coe = Optional.ofNullable(session.get(ContractualObligationEntity.class, id));
        ta.commit();
        return coe;
    }

    @Override
    public synchronized List<ContractualObligationEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<ContractualObligationEntity> coe = session.createQuery("SELECT a FROM ContractualObligationEntity a", ContractualObligationEntity.class).getResultList();
        List<ContractualObligationEntity> wrappedObligations = new ArrayList<>(coe.size());
        for(ContractualObligationEntity c: coe){
            wrappedObligations.add(c);
        }
        ta.commit();
        return wrappedObligations;
    }

    @Override
    public synchronized void save(ContractualObligationEntity obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(obligation);
        ta.commit();
    }

    @Override
    public synchronized ContractualObligationEntity update(ContractualObligationEntity obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.merge(obligation);
        ta.commit();
        return obligation;
    }

    @Override
    public synchronized void delete(ContractualObligationEntity obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(obligation);
        ta.commit();
    }
}