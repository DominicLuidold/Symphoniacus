package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IUserDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for handling the login for {@link IUserEntity}s.
 *
 * @author Valentin Goronjic
 */
public class LoginManager {
    private static final Logger LOG = LogManager.getLogger(LoginManager.class);
    protected IUserDao userDao;
    protected User currentLoggedInUser;

    public LoginManager() {
        this.userDao = new UserDao();
    }

    /**
     * Returns a {@link IUserEntity} object if the provided credentials match a known user.
     *
     * @param dto UserDto filled with shortcut and password
     * @return A User matching provided credentials
     */
    public Optional<LoginUserDto> login(LoginUserDto dto)
        throws NoSuchAlgorithmException {
        if (dto.getUserShortcut() == null || dto.getPassword() == null) {
            LOG.error("Login not possible - either userShortCut or userPassword is null");
            return Optional.empty();
        }

        // Get User
        Optional<IUserEntity> userEntity = this.userDao.loadUser(dto.getUserShortcut());
        if (userEntity.isEmpty()) {
            LOG.error("Credentials incorrect");
            return Optional.empty();
        }
        LOG.debug("Loaded user");

        // Generate Hash
        IUserEntity entity = userEntity.get();
        Optional<String> inputPasswordHash = entity.getHashFromPlaintext(dto.getPassword());
        if (inputPasswordHash.isEmpty()) {
            LOG.error("Login not possible");
            return Optional.empty();
        }

        // Compare Credentials
        boolean isLoginCorrect = this.userDao.isLoginCorrect(
            dto.getUserShortcut(),
            inputPasswordHash.get()
        );
        if (isLoginCorrect == false) {
            LOG.debug("Credentials invalid");
            return Optional.empty();
        }
        LOG.debug("Credentials valid");

        this.currentLoggedInUser = new User(userEntity.get());

        // Login attempt succeeded
        if (this.userDao.isUserMusician(this.currentLoggedInUser.getUserEntity())) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_MUSICIAN);
        } else if (
            this.userDao.isUserAdministrativeAssistant(
                this.currentLoggedInUser.getUserEntity()
            )
        ) {
            this.currentLoggedInUser.setType(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT);
        }
        LOG.debug(
            "Current Logged In User Type is: '{}'",
            this.currentLoggedInUser.getType()
        );

        IUserEntity e = this.currentLoggedInUser.getUserEntity();

        // create DTO
        LoginUserDto resultDto = new LoginUserDto.UserDtoBuilder(e.getUserId())
            .withUserShortcut(e.getShortcut())
            .withType(this.currentLoggedInUser.getType())
            .withFullName(this.currentLoggedInUser.getFullName())
            .build();

        return Optional.of(resultDto);
    }
}
