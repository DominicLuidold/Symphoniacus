package at.fhv.teamb.symphoniacus.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserController implements Initializable {

    @FXML
    private TextFlow txtFlowSection;

    @FXML
    private MenuItem userLogout;

    private ResourceBundle resourceBundle;

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @FXML
    void handleUserLogout(ActionEvent event) {
        try {
            MasterController.switchSceneTo(
                "/view/login.fxml", resourceBundle, this.txtFlowSection
            );
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }
}
