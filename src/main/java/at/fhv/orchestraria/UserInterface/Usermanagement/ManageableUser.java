package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.domain.Imodel.IAdministrativeAssistant;
import at.fhv.orchestraria.domain.Imodel.IMusician;


public interface ManageableUser {

    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    String getCountry();
    String getCity();
    String getZipCode();
    String getStreet();
    String getStreetNumber();
    IMusician getMusician();
    IAdministrativeAssistant getAdministrativeAssistant();
    String getShortcut();
    int getUserId();
}
