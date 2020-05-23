package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserController implements Initializable, Parentable<MainController> {

    private MainController parentController;
    private ResourceBundle resourceBundle;

    @FXML
    private Text txtSection;

    @FXML
    private Text txtRole;

    @FXML
    private MenuItem userLogout;

    @FXML
    private Menu userShortcut;

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @FXML
    void handleUserLogout(ActionEvent event) {
        try {
            MasterController.switchSceneTo(
                "/view/login.fxml", resourceBundle, this.txtSection
            );
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized UserController");
    }

    @Override
    public void setParentController(MainController controller) {
        this.parentController = controller;
    }

    @Override
    public MainController getParentController() {
        return this.parentController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        LOG.debug("Initialized UserController with parent");
    }

    public void setUserShortcut(String text) {
        this.userShortcut.setText(text);
    }

    public void setUserTxtSection(String section) {
        this.txtSection.setText(section);
    }

    public void setUserTxtRole(String role) {
        this.txtRole.setText(role);
    }
}
