package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.persistence.dao.AdministrativeAssistantDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager for Musicians.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class AdministrativeAssistantManager {
    private static final Logger LOG = LogManager.getLogger(AdministrativeAssistantManager.class);
    private AdministrativeAssistantDao aad;

    public AdministrativeAssistantManager() {
        this.aad = new AdministrativeAssistantDao();
    }

    /**
     * Returns a {@link MusicianEntity} based on provided {@link UserEntity}.
     *
     * <p>In case of the user not being a musician, an empty {@link Optional} will be returned.
     *
     * @param user The user to use
     * @return A Musician object representing the provided User
     */
    public Optional<AdministrativeAssistant> loadAdministrativeAssistant(UserEntity user) {
        if (user == null) {
            LOG.error("Cannot load musician with null user.");
            return Optional.empty();
        }
        return loadAdministrativeAssistant(user.getUserId());
    }

    /**
     * Loads a {@link MusicianEntity} by its identifier.
     * @param userId The userId of this Musician (NOT musicianId!)
     * @return Optional which is filled when loading worked, else empty
     */
    public Optional<AdministrativeAssistant> loadAdministrativeAssistant(int userId) {
        Optional<AdministrativeAssistantEntity> aa = this.aad.find(userId);

        // Load attempt failed
        if (aa.isEmpty()) {
            LOG.error("Could not load administrative assistant with userId {}", userId);
            return Optional.empty();
        }

        // Load attempt succeeded
        LOG.debug("Loaded Administrative Assistant with userId {}", userId);
        AdministrativeAssistant aaDomain = new AdministrativeAssistant(aa.get());
        return Optional.of(aaDomain);
    }
}
