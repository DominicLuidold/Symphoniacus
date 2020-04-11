package at.fhv.teamb.symphoniacus.application;


import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.LinkedList;
import java.util.List;

public class MusicianManager extends LogInManager {

    private Musician currentMusician;
    private UserDao userDao;

    public MusicianManager() {
        userDao = new UserDao();
    }

    /**
     * Is responsible for retrieving all roles of the musicians
     * from the database .
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public List<MusicianRole> getCurrentRole(User user) {
        if (user != null) {
            this.currentMusician = userDao.getUserIsMusician(user);
            if (currentMusician != null) {
                this.currentMusician.addAllMusicianRoles(
                    userDao.getAllMusicianRoles(currentMusician));
                return currentMusician.getMusicianRoles();
            }
        }
        return new LinkedList<>();
    }

    /**
     * Is responsible for retrieving corresponding sections of the musician
     * from the database .
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public List<Section> getSectionsAccessibleToUser(User user) {
        if (user != null) {
            if (userDao.getUserIsMusician(user) != null) {
                this.currentMusician = userDao.getUserIsMusician(user);
                if (this.currentMusician != null) {
                    this.currentMusician.setSection(
                        userDao.getSectionsAccessibleToUser(currentMusician));
                    return currentMusician.getSection();
                }
            }

        }
        return new LinkedList<>();
    }

}
