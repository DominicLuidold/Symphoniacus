package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.StatusBar;

public class TabPaneController implements Initializable, Parentable<MainController> {

    private static final Logger LOG = LogManager.getLogger(TabPaneController.class);
    @FXML
    public AnchorPane calendar;
    private MainController parentController;
    @FXML
    private StatusBar statusBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private DutySchedulerCalendarController dutySchedulerCalendarController;

    @FXML
    private OrganizationalOfficerCalendarController organizationalOfficerCalendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.debug("##########1");
        //this.dutySchedulerCalendarController.setParentController(this);
        //this.organizationalOfficerCalendar.setParentController(this);

        this.tabPane.getTabs().add(new Tab("Duty Roster"));
        this.tabPane.getTabs().add(new Tab("Duty Roster 2"));
        this.tabPane.getSelectionModel().clearSelection();

        this.tabPane.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                LOG.debug("Selected tab: {}", newValue.getText());
            }
        );
        MasterController mc = MasterController.getInstance();
        mc.setStatusBar(this.statusBar);
    }

    @Override
    public MainController getParentController() {
        return this.getParentController();
    }

    @Override
    public void setParentController(MainController controller) {
        LOG.debug("Initialized TabPaneParentController");
        this.parentController = controller;
    }
}
