package at.fhv.teamb.symphoniacus.application;


import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.LinkedList;
import java.util.List;

/**
 * Manager for Musicians.
 *
 * @author Danijel Antonijevic
 * @author Valentin Goronjic
 */
public class MusicianManager extends LogInManager {

    private Musician currentMusician;
    private MusicianDao musicianDao;

    public MusicianManager() {
        musicianDao = new MusicianDao();
    }

    /**
     * Is responsible for retrieving all roles of the musicians
     * from the database .
     **/
    public List<MusicianRole> getCurrentRole(User user) {
        // TODO this implementation is broken.
        // It will re-add the roles every time the GUI calls this
        if (user != null) {
            this.currentMusician = userDao.getUserIsMusician(user);
            if (this.currentMusician != null) {
                List<MusicianRole> roles = musicianDao.getAllMusicianRoles(currentMusician);
                for (MusicianRole mr : roles) {
                    this.currentMusician.addMusicianRole(mr);
                }
                return this.currentMusician.getMusicianRoles();
            }
        }
        return new LinkedList<>();
    }

    /**
     * Is responsible for retrieving corresponding sections of the musician
     * from the database .
     **/
    public List<Section> getSectionsAccessibleToUser(User user) {
        // TODO this implementation is broken.
        // It will re-set the sections every time the GUI calls this
        if (user != null) {
            if (this.userDao.getUserIsMusician(user) != null) {
                this.currentMusician = userDao.getUserIsMusician(user);
                if (this.currentMusician != null) {
                    this.currentMusician.setSection(
                        this.musicianDao.getSectionsAccessibleToUser(currentMusician));
                    return currentMusician.getSection();
                }
            }

        }
        return new LinkedList<>();
    }

}
