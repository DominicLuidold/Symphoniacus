package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IAdministrativeAssistant {
         int getUserId();
         String getDescription();
         IUser getUser();
}
