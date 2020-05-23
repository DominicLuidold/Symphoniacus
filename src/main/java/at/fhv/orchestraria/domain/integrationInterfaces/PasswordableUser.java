package at.fhv.orchestraria.domain.integrationInterfaces;

public interface PasswordableUser {

    String getPasswordSalt();
    String getPassword();
    void setPasswordSalt(String passwordSalt);
    void setPassword(String password) throws Exception;

}
