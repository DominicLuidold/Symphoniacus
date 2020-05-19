package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISubstitute {
     int getSubstituteId();
     String getFirstName();
     String getLastName();
     String getAddress();
     String getEmail();
     String getPhone();
     IMusician getMusician();
}
