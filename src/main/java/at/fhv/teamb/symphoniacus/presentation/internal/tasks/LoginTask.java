package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import java.util.Optional;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task that processes the GUI login.
 *
 * @author Valentin Goronjic
 * @see LoadingAnimationTask
 */
public class LoginTask extends LoadingAnimationTask<Optional<LoginUserDto>> {

    private static final Logger LOG = LogManager.getLogger(LoginTask.class);
    private LoginManager loginManager;
    private TextField userShortcut;
    private PasswordField pw;

    /**
     * Creates a new Login Task.
     *
     * @param loginManager The login manager that should be called
     * @param userShortcut The user shortcut TextField
     * @param pw The password PasswordField
     * @param pane The AnchorPane used to show the Loading Animation
     */
    public LoginTask(
        LoginManager loginManager,
        TextField userShortcut,
        PasswordField pw,
        AnchorPane pane
    ) {
        super(pane);
        this.loginManager = loginManager;
        this.userShortcut = userShortcut;
        this.pw = pw;
    }

    @Override
    protected Optional<LoginUserDto> call() throws Exception {
        super.call();
        LOG.debug("Processing login");
        // userId -1 in args => not yet available at this stage, not needed here
        LoginUserDto dto = new LoginUserDto.UserDtoBuilder(-1)
            .withUserShortcut(this.userShortcut.getText())
            .withPassword(this.pw.getText())
            .build();
        return loginManager.login(dto);
    }
}