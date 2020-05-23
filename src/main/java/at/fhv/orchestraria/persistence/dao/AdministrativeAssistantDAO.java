package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.AdministrativeAssistantEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class AdministrativeAssistantDAO implements DaoBase<AdministrativeAssistantEntity>{

    private SessionManager _sessionManager;

    protected AdministrativeAssistantDAO (SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<AdministrativeAssistantEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<AdministrativeAssistantEntity> administrativeAssistantEntity = Optional.ofNullable(session.get(AdministrativeAssistantEntity.class, id));
        ta.commit();
        return administrativeAssistantEntity;
    }

    @Override
    public synchronized List<AdministrativeAssistantEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<AdministrativeAssistantEntity> administrativeAssistantEntities = session.createQuery("SELECT a FROM AdministrativeAssistantEntity a",AdministrativeAssistantEntity.class).getResultList();
        List<AdministrativeAssistantEntity> wrappedAdministrativeAssistants = new ArrayList<>(administrativeAssistantEntities.size());
        for(AdministrativeAssistantEntity a : administrativeAssistantEntities){
            wrappedAdministrativeAssistants.add(a);
        }
        ta.commit();
        return wrappedAdministrativeAssistants;
    }

    @Override
    public synchronized void save(AdministrativeAssistantEntity assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(assistant);
        ta.commit();
    }

    @Override
    public synchronized AdministrativeAssistantEntity update(AdministrativeAssistantEntity assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        AdministrativeAssistantEntity aae = (AdministrativeAssistantEntity) session.merge(assistant);
        ta.commit();
        return aae;
    }

    @Override
    public synchronized void delete(AdministrativeAssistantEntity assistant) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(assistant);
        ta.commit();
    }
}
