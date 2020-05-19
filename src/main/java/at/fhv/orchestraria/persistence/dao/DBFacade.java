package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.persistence.dao.DaoBase;

public interface DBFacade {

    static DBFacade getInstance() {
        return JPADatabaseFacade.getInstance();
    }

    <T extends Object> DaoBase<T> getDAO(Class<T> clazz);

}
