package at.fhv.teamb.symphoniacus.application;


import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager for Musicians.
 *
 * @author Danijel Antonijevic
 * @author Valentin Goronjic
 */
public class MusicianManager extends LogInManager {

    private static final Logger LOG = LogManager.getLogger(MusicianManager.class);
    private Musician currentMusician;
    private MusicianDao musicianDao;

    public MusicianManager() {
        musicianDao = new MusicianDao();
    }

    public Optional<Musician> loadMusician(User u) {
        if (u == null) {
            LOG.error("Cannot load musician with null user.");
            return Optional.empty();
        }
        Optional<Musician> m = this.musicianDao.find(u.getUserId());
        if (m.isEmpty()) {
            LOG.error("Could not load musician with id {}", u.getUserId());
            return Optional.empty();
        }
        this.currentMusician = m.get();
        return m;
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
                    this.currentMusician = musicianDao.find(user.getUserId()).get();
                }
            }

        }
        return new LinkedList<>();
    }

}
