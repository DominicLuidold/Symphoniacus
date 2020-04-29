package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private TextField userShortcutField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

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
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        LOG.debug("Login btn pressed");
        Window owner = submitButton.getScene().getWindow();
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
    }
}
