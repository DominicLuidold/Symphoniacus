package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IUser {
     int getUserId();
     String getFirstName();
     String getLastName();
     String getShortcut();
     String getEmail();
     String getPhone();
     String getPassword();
     String getCity();
     String getZipCode();
     String getCountry();
     String getStreet();
     String getStreetNumber();
     String getPasswordSalt();
     IAdministrativeAssistant getAdministrativeAssistant();
     IMusician getMusician();
}
