package at.fhv.teamb.symphoniacus.persistence.model;

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
public class UserEntity {

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

    @OneToOne(mappedBy = "user")
    private MusicianEntity musician;

    @OneToMany(mappedBy = "user")
    private List<AdministrativeAssistantEntity> administrativeAssistants = new LinkedList<>();

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Generates a SHA-512 hash of the password and then sets it.
     * 
     * @param password Password in Plaintext.
     * @throws Exception When hash cannot be generated
     */
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

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public MusicianEntity getMusician() {
        return this.musician;
    }

    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
        musician.setUser(this);
    }

    public List<AdministrativeAssistantEntity> getAdministrativeAssistants() {
        return this.administrativeAssistants;
    }

    public void addAdministrativeAssistant(AdministrativeAssistantEntity administrativeAssistant) {
        this.administrativeAssistants.add(administrativeAssistant);
        administrativeAssistant.setUser(this);
    }

    public void removeAdministrativeAssistant(
        AdministrativeAssistantEntity administrativeAssistant
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
