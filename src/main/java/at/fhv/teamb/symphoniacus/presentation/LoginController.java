package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.LoginTask;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * GUI Controller responsible for processing the Login.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
public class LoginController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(LoginController.class);
    private final ValidationSupport validationSupport = new ValidationSupport();
    @FXML
    private AnchorPane pane;
    @FXML
    private GridPane grid;
    @FXML
    private TextField userShortcutField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    private LoginManager loginManager;
    private boolean isValid = false;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.loginManager = new LoginManager();
        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);
        this.validationSupport.registerValidator(this.userShortcutField,
            Validator.createEmptyValidator(resources.getString("login.error.shortcut.missing"))
        );
        this.validationSupport.registerValidator(this.passwordField,
            Validator.createEmptyValidator(resources.getString("login.error.password.missing"))
        );
        this.validationSupport.validationResultProperty().addListener((o, oldVal, newVal) -> {
            this.isValid = newVal.getErrors().isEmpty();
            LOG.debug("Is Login input valid? {}", this.isValid);
            this.submitButton.setDisable(!this.isValid);
        });

        // do not focus user shortcut on first-time load
        // https://stackoverflow.com/a/29058225
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        this.userShortcutField.focusedProperty().addListener(
            (observable, oldValue, newValue)
                -> {
                if (Boolean.TRUE.equals(newValue) && firstTime.get()) {
                    this.grid.requestFocus();
                    firstTime.setValue(false);
                }
            });
    }

    /**
     * This function processes the login data using the {@link LoginManager} and checks if there
     * is a corresponding user. If this user exists, an attribute is set to which
     * {@link at.fhv.teamb.symphoniacus.application.type.DomainUserType} he belongs. After that the
     * method {@link #loadMainScene(LoginUserDto user)} is called.
     * Otherwise the user gets an error message for the failed login.
     */
    public void processLoginCredentials() {
        LOG.debug("Login btn pressed");
        Window owner = this.submitButton.getScene().getWindow();
        if (!this.isValid) {
            LOG.info("Login input is not valid (missing required fields)");
            StringBuilder sb = new StringBuilder();
            for (ValidationMessage vm : this.validationSupport.getValidationResult().getErrors()) {
                sb.append(vm.getText());
                sb.append("\n");
            }
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                owner,
                this.resources.getString("login.error.missing.fields.title"),
                sb.toString()
            );
            return;
        }
        LoginTask task = new LoginTask(
            this.loginManager,
            this.userShortcutField,
            this.passwordField,
            this.pane
        );
        Thread thread = new Thread(task, "Login Task");
        thread.start();

        task.setOnSucceeded(event -> {
            Optional<LoginUserDto> userOptional = task.getValue();

            LOG.debug(
                "Login with Username: {} and Password {}",
                this.userShortcutField.getText(),
                "************"
            );

            if (userOptional.isPresent()) {
                LoginUserDto dto = userOptional.get();

                // taken from OWASP ASVS V2.2 General Authenticator Requirements
                // making sure we have a good Acess Control for Security Reasons
                if (
                    dto.getUserShortcut().equalsIgnoreCase(
                        resources.getString(
                            "login.error.banned.user1"
                        )
                    )
                ) {
                    Alert successAlert = new Alert(Alert.AlertType.ERROR);
                    successAlert.setTitle(resources.getString("login.error.failed.title"));
                    successAlert.getButtonTypes()
                        .setAll(new ButtonType(resources.getString("global.button.ok")));

                    // Get custom wasted icon
                    ImageView icon = new ImageView("images/wasted.png");
                    //icon.setFitHeight(200);
                    //icon.setFitWidth(300);
                    successAlert.setGraphic(icon);
                    successAlert.setHeaderText(" ");
                    successAlert.setWidth(300);
                    successAlert.setHeight(150);
                    successAlert.show();
                    return;
                }

                loadMainScene(dto);
            } else {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner,
                    this.resources.getString("login.error.failed.title"),
                    this.resources.getString("login.error.failed.message")
                );
            }
        });

    }

    private void loadMainScene(LoginUserDto user) {
        Locale locale = new Locale("en", "UK");
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            MainController controller = MasterController.switchSceneTo(
                "/view/mainWindow.fxml",
                bundle,
                this.submitButton
            );
            controller.setLoginUser(user);
            LOG.debug("MainController is fully loaded now :-)");
        } catch (IOException e) {
            LOG.error("Cannot load main scene", e);
            Stage owner = (Stage) this.submitButton.getScene().getWindow();
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                owner,
                this.resources.getString("login.error.failed.title"),
                this.resources.getString("login.error.technical.problems")
            );
        }
    }
}
