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
        _daos.put(DutyEntity.class, new DutyDAO(_sessionManager));
        _daos.put(MusicianEntity.class, new MusicianDAO(_sessionManager));
        _daos.put(DutyPositionEntity.class, new DutyPositionDAO(_sessionManager));
        _daos.put(InstrumentationEntity.class, new InstrumentationDAO(_sessionManager));
        _daos.put(InstrumentationPositionEntity.class, new InstrumentationPositionDAO(_sessionManager));
        _daos.put(SectionInstrumentationEntity.class, new SectionInstrumentationDAO(_sessionManager));
        _daos.put(SectionEntity.class, new SectionDAO(_sessionManager));
        _daos.put(UserEntity.class, new UserDAO(_sessionManager));
        _daos.put(InstrumentCategoryEntity.class, new InstrumentCategoryDAO(_sessionManager));
        _daos.put(MusicianRoleEntity.class, new MusicianRoleDAO(_sessionManager));
        _daos.put(InstrumentCategoryMusicianEntity.class, new InstrumentCategoryMusicianDAO(_sessionManager));
        _daos.put(MusicianRoleMusicianEntity.class, new MusicianRoleMusicianDAO(_sessionManager));
        _daos.put(ContractualObligationEntity.class, new ContractualObligationDAO(_sessionManager));
        _daos.put(AdministrativeAssistantEntity.class, new AdministrativeAssistantDAO(_sessionManager));

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
