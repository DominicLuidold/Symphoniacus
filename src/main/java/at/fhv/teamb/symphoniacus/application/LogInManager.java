package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.roleenum.DomainUserType;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogInManager {

    private static final Logger LOG = LogManager.getLogger(LogInManager.class);
    protected UserDao userDao;
    protected User currentLoggedInUser;

    public LogInManager() {
        this.userDao = new UserDao();
    }

    public Optional<User> login(String userShortCut, String userPassword) {

        if (userShortCut != null && userPassword != null) {
            Optional<User> user = this.userDao.login(userShortCut, userPassword);
            if (user.isEmpty()) {
                LOG.error("Login not possible");
                return user;
            }

            if (this.userDao.isUserMusician(this.currentLoggedInUser) == true) {
                user.get().setType(DomainUserType.DOMAIN_MUSICIAN);
            } else if (this.userDao.getUserIsAdministrativeAssistant != null) {
                //TODO
            }
            this.currentLoggedInUser = user.get();
            return user;
        }
        LOG.error("Login not possible, either userShortCut or userPassword is null");
        return Optional.empty();
    }
}

