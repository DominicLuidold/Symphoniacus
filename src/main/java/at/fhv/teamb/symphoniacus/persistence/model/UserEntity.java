package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.orchestraria.domain.integrationInterfaces.PasswordableUser;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "user")
public class UserEntity implements IUserEntity, PasswordableUser {
    private static final Logger LOG = LogManager.getLogger(UserEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "shortcut")
    private String shortcut;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "street")
    private String street;

    @Column(name = "streetNumber")
    private String streetNumber;

    @Column(name = "passwordSalt")
    private String passwordSalt;

    @OneToOne(mappedBy = "user", targetEntity = MusicianEntity.class)
    private IMusicianEntity musician;

    @OneToMany(mappedBy = "user", targetEntity = AdministrativeAssistantEntity.class)
    private List<IAdministrativeAssistantEntity> administrativeAssistants = new LinkedList<>();

    @Override
    public Integer getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getShortcut() {
        return this.shortcut;
    }

    @Override
    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Generates a SHA-512 hash of the password and then sets it.
     *
     * @param password Password in Plaintext.
     * @throws Exception When hash cannot be generated
     */
    @Override
    public void setPassword(String password) throws Exception {
        if (this.passwordSalt == null) {
            LOG.debug("Generating salt");
            this.passwordSalt = generateSalt();
        }
        Optional<String> hash = getHashFromPlaintext(password);
        if (hash.isPresent()) {
            this.password = hash.get();
        } else {
            LOG.error("Could not generate hash");
        }
    }

    // this is needed by Team C
    // but we don't wanna give our passwords to anyone, to be honest...
    // so let's just pretend that we give them something :-)
    public String getPassword() {
        return "";
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getStreetNumber() {
        return this.streetNumber;
    }

    @Override
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    @Override
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public IMusicianEntity getMusician() {
        return this.musician;
    }

    @Override
    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
        musician.setUser(this);
    }

    @Override
    public List<IAdministrativeAssistantEntity> getAdministrativeAssistants() {
        return this.administrativeAssistants;
    }

    @Override
    public void addAdministrativeAssistant(IAdministrativeAssistantEntity administrativeAssistant) {
        this.administrativeAssistants.add(administrativeAssistant);
        administrativeAssistant.setUser(this);
    }

    @Override
    public void removeAdministrativeAssistant(
        IAdministrativeAssistantEntity administrativeAssistant
    ) {
        this.administrativeAssistants.remove(administrativeAssistant);
        administrativeAssistant.setUser(null);
    }

    /**
     * Generates a SHA-512 hash from a given password.
     *
     * @param password Password as plaintext
     * @return Optional, which if is present contains the hashed password
     * @throws NoSuchAlgorithmException When SHA-512 is lost
     */
    @Override
    public Optional<String> getHashFromPlaintext(String password)
        throws NoSuchAlgorithmException {
        // https://www.geeksforgeeks.org/sha-512-hash-in-java/
        if (this.passwordSalt == null) {
            LOG.error("No salt present, cannot generate hash");
            return Optional.empty();
        }
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(this.passwordSalt.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, md.digest(password.getBytes()));

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        // return the HashText
        return Optional.of(hashtext);
    }

    /**
     * Generates a salt.
     *
     * @return Salt
     */
    @Override
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        BigInteger no = new BigInteger(1, salt);
        String saltAsText = no.toString(16);
        while (saltAsText.length() < 32) {
            saltAsText = "0" + saltAsText;
        }

        return saltAsText;
    }
}
