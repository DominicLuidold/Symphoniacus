package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.util.Optional;

public interface IUserDao extends Dao<IUserEntity> {

    /**
     * Returns a {@link IUserEntity} if provided shortcut and password match a database entry.
     *
     * @param userShortCut The shortcut to identify the user
     * @return A user matching provided credentials
     */
    Optional<IUserEntity> loadUser(String userShortCut);

    /**
     * Checks whether the given userShortcut + password hash are valid.
     *
     * @param userShortCut      current user's shortcut
     * @param inputPasswordHash current user's password hash
     * @return true if credentials are correct
     */
    boolean isLoginCorrect(String userShortCut, String inputPasswordHash);

    /**
     * Checks whether the provided {@link IUserEntity} is a {@link MusicianEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a musician, false otherwise
     */
    boolean isUserMusician(IUserEntity currentUser);

    /**
     * Checks whether the provided {@link IUserEntity} is a {@link AdministrativeAssistantEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a AdministrativeAssistant, false otherwise
     */
    boolean isUserAdministrativeAssistant(IUserEntity currentUser);
}
