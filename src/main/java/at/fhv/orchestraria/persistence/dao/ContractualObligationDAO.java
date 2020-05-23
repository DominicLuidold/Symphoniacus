package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.ContractualObligationEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class ContractualObligationDAO implements DaoBase<ContractualObligationEntityC> {

    private SessionManager _sessionManager;

    public ContractualObligationDAO(SessionManager sessionManager){
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<ContractualObligationEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<ContractualObligationEntityC> coe = Optional.ofNullable(session.get(ContractualObligationEntityC.class, id));
        ta.commit();
        return coe;
    }

    @Override
    public synchronized List<ContractualObligationEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<ContractualObligationEntityC> coe = session.createQuery("SELECT a FROM ContractualObligationEntity a", ContractualObligationEntityC.class).getResultList();
        List<ContractualObligationEntityC> wrappedObligations = new ArrayList<>(coe.size());
        for(ContractualObligationEntityC c: coe){
            wrappedObligations.add(c);
        }
        ta.commit();
        return wrappedObligations;
    }

    @Override
    public synchronized void save(ContractualObligationEntityC obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(obligation);
        ta.commit();
    }

    @Override
    public synchronized ContractualObligationEntityC update(ContractualObligationEntityC obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.merge(obligation);
        ta.commit();
        return obligation;
    }

    @Override
    public synchronized void delete(ContractualObligationEntityC obligation) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(obligation);
        ta.commit();
    }
}