package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface IUserEntity {
    Integer getUserId();

    void setUserId(Integer userId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getShortcut();

    void setShortcut(String shortcut);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    void setPassword(String password) throws Exception;

    String getCity();

    void setCity(String city);

    String getZipCode();

    void setZipCode(String zipCode);

    String getCountry();

    void setCountry(String country);

    String getStreet();

    void setStreet(String street);

    String getStreetNumber();

    void setStreetNumber(String streetNumber);

    String getPasswordSalt();

    void setPasswordSalt(String passwordSalt);

    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);

    List<IAdministrativeAssistantEntity> getAdministrativeAssistants();

    void addAdministrativeAssistant(IAdministrativeAssistantEntity administrativeAssistant);

    void removeAdministrativeAssistant(IAdministrativeAssistantEntity administrativeAssistant);

    Optional<String> getHashFromPlaintext(String password) throws NoSuchAlgorithmException;

    String generateSalt();
}
