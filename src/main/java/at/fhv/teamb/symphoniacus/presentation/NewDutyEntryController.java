package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.LoginTask;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
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

public class NewDutyEntryController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(NewDutyEntryController.class);

    @FXML
    private AnchorPane pane;

    private ResourceBundle resources;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }


}
