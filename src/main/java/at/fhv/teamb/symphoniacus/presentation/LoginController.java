package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

public class LoginController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(LoginController.class);
    @FXML
    private GridPane grid;

    @FXML
    private TextField userShortcutField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private ImageView image;

    @FXML
    private Label label;

    private boolean isValid = false;
    private ValidationSupport validationSupport = new ValidationSupport();
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);
        this.validationSupport.registerValidator(this.userShortcutField,
            Validator.createEmptyValidator(resources.getString(
                "login.error.usershortcutmissing"
                )
            )
        );
        this.validationSupport.registerValidator(this.passwordField,
            Validator.createEmptyValidator(resources.getString(
                "login.error.passwordmissing"
                )
            )
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
                if (newValue && firstTime.get()) {
                    this.grid.requestFocus();
                    firstTime.setValue(false);
                }
            });
    }

    public void processLoginCredentials(ActionEvent actionEvent) {
        LOG.debug("Login btn pressed");
        Window owner = this.submitButton.getScene().getWindow();
        if (!this.isValid) {
            LOG.info("Login input is not valid (missing required fields)");
            StringBuilder sb = new StringBuilder();
            for (ValidationMessage vm : this.validationSupport.getValidationResult().getErrors()) {
                sb.append(vm.getText());
                sb.append("\n");
            }
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, this.resources.getString(
                "login.error.missingrequiredfields.title"
                ),
                sb.toString());
            return;
        }
        LOG.debug("Login credentials filled in, checking credentials now");
        LOG.error("MISSING credentials check");
        loadMainScene(); // TODO add loginUser here as param
    }

    // TODO change to accept login user as param here
    private void loadMainScene() {
        Locale locale = new Locale("en", "UK");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/view/mainWindow.fxml"),
                bundle);

            Parent mainRoot = loader.load();
            MainController controller = loader.getController();
            LOG.debug("MainController is fully loaded now :-)");
            // controller.setLoginUser(user) from params
            Scene currentScene = this.submitButton.getScene();
            Stage owner = (Stage) this.submitButton.getScene().getWindow();
            Scene newScene = new Scene(mainRoot, currentScene.getWidth(), currentScene.getHeight());
            owner.setScene(newScene);
        } catch (IOException e) {
            LOG.error(e);
            Stage owner = (Stage) this.submitButton.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login failed",
                this.resources.getString(
                "login.error.login.technical.problems"
                )
            );
            return;
        }
    }
}
