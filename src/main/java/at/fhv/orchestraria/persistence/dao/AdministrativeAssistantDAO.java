package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.AdministrativeAssistantEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class AdministrativeAssistantDAO implements DaoBase<AdministrativeAssistantEntityC>{

    private SessionManager _sessionManager;

    protected AdministrativeAssistantDAO (SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<AdministrativeAssistantEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<AdministrativeAssistantEntityC> administrativeAssistantEntity = Optional.ofNullable(session.get(AdministrativeAssistantEntityC.class, id));
        ta.commit();
        return administrativeAssistantEntity;
    }

    @Override
    public synchronized List<AdministrativeAssistantEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<AdministrativeAssistantEntityC> administrativeAssistantEntities = session.createQuery("SELECT a FROM AdministrativeAssistantEntity a", AdministrativeAssistantEntityC.class).getResultList();
        List<AdministrativeAssistantEntityC> wrappedAdministrativeAssistants = new ArrayList<>(administrativeAssistantEntities.size());
        for(AdministrativeAssistantEntityC a : administrativeAssistantEntities){
            wrappedAdministrativeAssistants.add(a);
        }
        ta.commit();
        return wrappedAdministrativeAssistants;
    }

    @Override
    public synchronized void save(AdministrativeAssistantEntityC assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(assistant);
        ta.commit();
    }

    @Override
    public synchronized AdministrativeAssistantEntityC update(AdministrativeAssistantEntityC assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        AdministrativeAssistantEntityC aae = (AdministrativeAssistantEntityC) session.merge(assistant);
        ta.commit();
        return aae;
    }

    @Override
    public synchronized void delete(AdministrativeAssistantEntityC assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(assistant);
        ta.commit();
    }
}
