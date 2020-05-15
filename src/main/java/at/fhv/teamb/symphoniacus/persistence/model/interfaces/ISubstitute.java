package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

public interface ISubstitute {
    Integer getSubstituteId();

    void setSubstituteId(Integer substituteId);

    Integer getMusicianId();

    void setMusicianId(Integer musicianId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getAddress();

    void setAddress(String address);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);
}
