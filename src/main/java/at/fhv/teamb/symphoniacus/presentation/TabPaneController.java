package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.StatusBar;

public class TabPaneController implements Initializable, Parentable<MainController> {

    private static final Logger LOG = LogManager.getLogger(TabPaneController.class);
    private MainController parentController;

    @FXML
    public AnchorPane calendar;

    @FXML
    private StatusBar statusBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private CalendarController calendarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.calendarController.setParentController(this);
        MasterController mc = MasterController.getInstance();
        mc.setStatusBar(this.statusBar);
    }

    @Override
    public void setParentController(MainController controller) {
        LOG.debug("Initialized TabPaneParentController");
        this.parentController = controller;
    }
}
