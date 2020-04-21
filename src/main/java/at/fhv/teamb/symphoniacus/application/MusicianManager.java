package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager for Musicians.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class MusicianManager extends LoginManager {
    private static final Logger LOG = LogManager.getLogger(MusicianManager.class);
    private MusicianDao musicianDao;

    public MusicianManager() {
        this.musicianDao = new MusicianDao();
    }

    /**
     * Returns a {@link MusicianEntity} based on provided {@link User}.
     *
     * <p>In case of the user not being a musician, an empty {@link Optional} will be returned.
     *
     * @param user The user to use
     * @return A Musician object representing the provided User
     */
    public Optional<MusicianEntity> loadMusician(User user) {
        if (user == null) {
            LOG.error("Cannot load musician with null user.");
            return Optional.empty();
        }
        Optional<MusicianEntity> musician = this.musicianDao.find(user.getUserId());

        // Load attempt failed
        if (musician.isEmpty()) {
            LOG.error("Could not load musician with userId {}", user.getUserId());
            return Optional.empty();
        }

        // Load attempt succeeded
        return musician;
    }
}
