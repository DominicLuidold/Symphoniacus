package at.fhv.orchestraria.application;

import at.fhv.orchestraria.persistence.dao.JPADatabaseFacade;
import at.fhv.orchestraria.persistence.dao.UserDAO;
import at.fhv.orchestraria.domain.integrationInterfaces.PasswordableUser;

import at.fhv.orchestraria.domain.model.UserEntity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.NoResultException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * Handles the hashing of passwords and their corresponding salts. Also responsible for randomly generating the salt
 * if a new password is entered and saving this information.
 */
public class PasswordManager {


    //TODO: Remove
    public static void main(String[] args) {
//        try {
//            UserEntity user =JPADatabaseFacade.getUserDAO().get(92).get(); //albblo
//            setNewPassword(user, "password");
//
//            JPADatabaseFacade.getUserDAO().save(user);
//
//            user =JPADatabaseFacade.getUserDAO().get(93).get(); //stafri
//
//            setNewPassword(user, "password");
//
//            JPADatabaseFacade.getUserDAO().save(user);
//        } catch (PasswordException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Checks if the provided password is valid.
     * @param user      User the password may belong to.
     * @param password  The password to check.
     * @return          True if the password is correct.
     * @throws PasswordException  In case of an error while hashing the password.
     */
    public static boolean isPasswordValid(PasswordableUser user, String password) throws PasswordException {
        String hashedPassword = null;
        try {
            byte[] passwordByte = null;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), user.getPasswordSalt().getBytes(StandardCharsets.US_ASCII), 65536, 2040);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            passwordByte = factory.generateSecret(spec).getEncoded();

            StringBuilder sb = new StringBuilder();
            for (byte b : passwordByte) {
                sb.append(Integer.toString((b & 0xff) + 0x100, Character.MAX_RADIX).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordException("Password could not be created");
        }
        if(hashedPassword == null){
            throw new PasswordException("Password could not be created");
        }
        return hashedPassword.equals(user.getPassword());
    }


    /**
     * Sets the password of the provided user to the provided password but does not save him in a database.
     * @param user     The user where the new password shall be set.
     * @param password The new password of the user.
     * @throws PasswordException In case of an error while hashing the password.
     */
    public static void setNewPassword(PasswordableUser user, String password) throws PasswordException {
        if(password.length()<6) {
            throw new PasswordException("Password is too short.");
        }

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[255];
        random.nextBytes(salt);

        user.setPasswordSalt(new String(salt));

        String hashedPassword = null;
        try {
            byte[] passwordByte = null;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), user.getPasswordSalt().getBytes(StandardCharsets.US_ASCII), 65536, 2040);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            passwordByte = factory.generateSecret(spec).getEncoded();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< passwordByte.length ;i++)
                sb.append(Integer.toString((passwordByte[i] & 0xff) + 0x100, Character.MAX_RADIX).substring(1));
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordException("Password could not be created");
        }
        if(hashedPassword == null){
            throw new PasswordException("Password could not be created");
        }
        try {
            user.setPassword(hashedPassword);
        }catch (Exception e) {
            System.out.println("lul");
        }
    }


    /**
     * Checks if there is a shortcut - password comnination in the database and returns the corresponding user.
     * @param shortcut Shortcut of the user where the combination shall be checked.
     * @param password Password of the user where the combination shall be checked.
     * @return The user if the combination was correct or an empty Optional if it was not.
     */
    public static Optional<PasswordableUser> getUserIfValid(String shortcut, String password) {
        Optional<PasswordableUser> user;
        try {
            UserDAO uDAO = (UserDAO)JPADatabaseFacade.getInstance().getDAO(UserEntity.class);
            user = Optional.ofNullable(uDAO.getByShortcut(shortcut).get());
            if (user.isPresent() && !isPasswordValid(user.get(), password)) {
                user = Optional.empty();
            }
        } catch (NoResultException | PasswordException | NoSuchElementException ex) {
            user = Optional.empty();
        }
        return user;
    }

}
