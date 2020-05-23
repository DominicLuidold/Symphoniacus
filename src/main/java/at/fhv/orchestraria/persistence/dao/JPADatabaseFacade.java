package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.*;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPADatabaseFacade implements DBFacade {

    private EntityManager _entityManager;
    private SessionFactory _sessionFactory;
    private SessionManager _sessionManager;

    private static JPADatabaseFacade _instance;

    private Map<Class, DaoBase> _daos;


    public static JPADatabaseFacade getInstance() {
        if (null == _instance) {
            _instance = new JPADatabaseFacade();
        }
        return _instance;
    }

    private JPADatabaseFacade(){
        _entityManager = Persistence.createEntityManagerFactory("domp").createEntityManager();
        _sessionFactory = Persistence.createEntityManagerFactory("domp").unwrap(SessionFactory.class);
        _sessionManager = new SessionManager(_sessionFactory);

        _daos= new HashMap<>();
        _daos.put(DutyEntityC.class, new DutyDAO(_sessionManager));
        _daos.put(MusicianEntityC.class, new MusicianDAO(_sessionManager));
        _daos.put(DutyPositionEntityC.class, new DutyPositionDAO(_sessionManager));
        _daos.put(InstrumentationEntityC.class, new InstrumentationDAO(_sessionManager));
        _daos.put(InstrumentationPositionEntityC.class, new InstrumentationPositionDAO(_sessionManager));
        _daos.put(SectionInstrumentationEntityC.class, new SectionInstrumentationDAO(_sessionManager));
        _daos.put(SectionEntityC.class, new SectionDAO(_sessionManager));
        _daos.put(UserEntityC.class, new UserDAO(_sessionManager));
        _daos.put(InstrumentCategoryEntityC.class, new InstrumentCategoryDAO(_sessionManager));
        _daos.put(MusicianRoleEntityC.class, new MusicianRoleDAO(_sessionManager));
        _daos.put(InstrumentCategoryMusicianEntityC.class, new InstrumentCategoryMusicianDAO(_sessionManager));
        _daos.put(MusicianRoleMusicianEntityC.class, new MusicianRoleMusicianDAO(_sessionManager));
        _daos.put(ContractualObligationEntityC.class, new ContractualObligationDAO(_sessionManager));
        _daos.put(AdministrativeAssistantEntityC.class, new AdministrativeAssistantDAO(_sessionManager));

    }

    public <T extends Object> DaoBase<T> getDAO(Class<T> clazz){
        return _daos.get(clazz);
    }

   public void closeSession(){_sessionManager.closeSession();}


   //Damit die JUNIT Tests Funktionieren! TODO Remove
//    public static void resetFacade(){
//        if(_instance!=null) {
//            _instance._entityManager.clear();
//            _instance = new JPADatabaseFacade();
//        }
//    }
}
