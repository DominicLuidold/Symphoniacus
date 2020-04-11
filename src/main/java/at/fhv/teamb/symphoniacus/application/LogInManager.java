package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import at.fhv.teamb.symphoniacus.roleEnum.DomainUserType;


public class LogInManager {

    protected UserDao userDao;
    protected User currentLoggedInUser;

    public LogInManager() {
        this.userDao = new UserDao();
    }

    /**
     * Checks the input data of the user who wants to log on, if successful then a user is returned.
     *
     * @author : Danijel Antonijevic
     * @created : 10.04.20, Fr.
     **/
    public User login(String userShortCut, String userPassword) {

        if (userShortCut != null && userPassword != null) {
            this.currentLoggedInUser = this.userDao.login(userShortCut, userPassword);

            if (this.currentLoggedInUser != null) {
                if (this.userDao.getUserIsMusician(this.currentLoggedInUser) != null) {
                    this.currentLoggedInUser.setType(DomainUserType.DOMAIN_MUSICIAN);
                } else if (this.userDao.getUserIsAdministrativeAssistant != null) {
                    //TODO
                }
            }
        }
        return this.currentLoggedInUser;
    }
}

