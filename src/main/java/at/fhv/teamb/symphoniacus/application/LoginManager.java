package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
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
    public Optional<User> login(String userShortCut, String userPassword) { // TODO
        if (userShortCut == null || userPassword == null) {
            LOG.error("Login not possible - either userShortCut or userPassword is null");
            return Optional.empty();
        }
        Optional<UserEntity> userEntity = this.userDao.login(userShortCut, userPassword);

        // Login attempt failed
        if (userEntity.isEmpty()) {
            LOG.error("Login not possible");
            return Optional.empty();
        } else {
            this.currentLoggedInUser = new User(userEntity.get());
        }

        // Login attempt succeeded
        if (this.userDao.isUserMusician(this.currentLoggedInUser.getUserEntity())) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_MUSICIAN);
        } // TODO - More types

        if (this.userDao.isUserAdministrativeAssistant(this.currentLoggedInUser.getUserEntity())) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT);
        }

        return Optional.of(this.currentLoggedInUser);
    }
}
