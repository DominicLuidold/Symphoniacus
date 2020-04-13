package at.fhv.teamb.symphoniacus.application;


import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
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

    public Section getSectionsAccessibleToUser(User user) {
        if (user != null) {
            if (this.userDao.isUserMusician(user) == true) {
                this.currentMusician = musicianDao.find(user.getUserId()).get();
            }
        } else {
            LOG.error("Cannot load section for null user");
        }

        return this.currentMusician.getSection();
    }

}
