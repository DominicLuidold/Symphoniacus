package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.IUserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager for Musicians.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class MusicianManager {
    private static final Logger LOG = LogManager.getLogger(MusicianManager.class);
    private MusicianDao musicianDao;

    public MusicianManager() {
        this.musicianDao = new MusicianDao();
    }

    /**
     * Returns a {@link Musician} based on provided {@link IUserEntity}.
     *
     * <p>In case of the user not being a musician, an empty {@link Optional} will be returned.
     *
     * @param user The user to use
     * @return A Musician object representing the provided User
     */
    public Optional<Musician> loadMusician(IUserEntity user) {
        if (user == null) {
            LOG.error("Cannot load musician with null user.");
            return Optional.empty();
        }
        return loadMusician(user.getUserId());
    }

    /**
     * Loads a {@link Musician} by its identifier.
     *
     * @param userId The userId of this Musician (NOT musicianId!)
     * @return Optional which is filled when loading worked, else empty
     */
    public Optional<Musician> loadMusician(int userId) {
        Optional<MusicianEntity> musicianEntity = this.musicianDao.find(userId);

        // Load attempt failed
        if (musicianEntity.isEmpty()) {
            LOG.error("Could not load musician with userId {}", userId);
            return Optional.empty();
        }

        // Load attempt succeeded
        LOG.debug(
            "Loaded musician with musicianId {}",
            musicianEntity.get().getMusicianId()
        );
        return Optional.of(new Musician(musicianEntity.get()));
    }

}
