package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.domain.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @FXML
    public AnchorPane userHeaderMenu;

    @FXML
    public AnchorPane tabPane;

    @FXML
    private TabPaneController tabPaneController;

    @FXML
    private UserController userHeaderMenuController;

    private User currentUser;

    public void setLoginUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userHeaderMenuController.setParentController(this);
        this.tabPaneController.setParentController(this);
        LOG.debug("Initialized MainController");
        LOG.debug(tabPaneController);
    }
}
