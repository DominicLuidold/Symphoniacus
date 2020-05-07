package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for handling the login for {@link UserEntity}s.
 *
 * @author Valentin Goronjic
 */
public class LoginManager {
    private static final Logger LOG = LogManager.getLogger(LoginManager.class);
    protected UserDao userDao;
    protected User currentLoggedInUser;

    public LoginManager() {
        this.userDao = new UserDao();
    }

    /**
     * Returns a {@link UserEntity} object if the provided credentials match a known user.
     *
     * @param userShortCut The shortcut to identify the user
     * @param userPassword The password to authenticate the user
     * @return A User matching provided credentials
     */
    public Optional<User> login(String userShortCut, String userPassword)
        throws NoSuchAlgorithmException {
        if (userShortCut == null || userPassword == null) {
            LOG.error("Login not possible - either userShortCut or userPassword is null");
            return Optional.empty();
        }

        // Get User
        Optional<UserEntity> userEntity = this.userDao.loadUser(userShortCut);
        if (userEntity.isEmpty()) {
            LOG.error("Credentials incorrect");
            return Optional.empty();
        }
        LOG.debug("Loaded user");

        // Generate Hash
        UserEntity entity = userEntity.get();
        Optional<String> inputPasswordHash = entity.getHashFromPlaintext(userPassword);
        if (inputPasswordHash.isEmpty())  {
            LOG.error("Login not possible");
            return Optional.empty();
        }

        // Compare Credentials
        boolean isLoginCorrect = this.userDao.isLoginCorrect(userShortCut, inputPasswordHash.get());
        if (isLoginCorrect == false) {
            LOG.debug("Credentials invalid");
            return Optional.empty();
        }
        LOG.debug("Credentials valid");

        this.currentLoggedInUser = new User(userEntity.get());

        // Login attempt succeeded
        if (this.userDao.isUserMusician(this.currentLoggedInUser.getUserEntity())) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_MUSICIAN);
        } else if (
            this.userDao.isUserAdministrativeAssistant(
                this.currentLoggedInUser.getUserEntity()
            )
        ) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT);
        }
        LOG.debug(
            "Current Logged In User Type is: '{}'",
            this.currentLoggedInUser.getType()
        );

        return Optional.of(this.currentLoggedInUser);
    }
}
