package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    @FXML
    private StatusBar statusBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private DutySchedulerCalendarController dutySchedulerCalendarController;

    @FXML
    private OrganizationalOfficerCalendarController organizationalOfficerCalendar;

    private MainController parentController;
    private ResourceBundle bundle;

    private void removeTab(String title) {
        ObservableList<Tab> tabs = this.tabPane.getTabs();
        for (Tab t : tabs) {
            if (t.getText().equals(title)) {
                this.tabPane.getTabs().remove(t);
            }
        }
    }

    protected void removeTab(TabPaneEntry entry) {
        removeTab(entry.getTitle());
    }

    protected void addTab(TabPaneEntry entry) {
        Tab tab = new Tab(entry.getTitle());
        FXMLLoader loader = new FXMLLoader(
            this.getClass().getResource(entry.getFxmlPath()),
            this.bundle
        );

        try {
            Parent p = loader.load();
            tab.setContent(p);
            Parentable controller = loader.getController();
            if (controller != null) {
                if (controller.getParentController() == null) {
                    controller.setParentController(this);
                }
                controller.initializeNew();
            }
        } catch (IOException e) {
            LOG.error(e);
        }

        this.tabPane.getTabs().add(tab);
    }

    protected void initializeTabMenu() throws IOException {
        MainController parent = this.getParentController();

        Queue<TabPaneEntry> tabs = new LinkedList<>();
        if (parent.getLoginUserType().equals(DomainUserType.DOMAIN_MUSICIAN)) {
            tabs = parent.getPermittedTabs(
                parent.getLoginUserType(),
                parent.getCurrentMusician(),
                this.bundle
            );
        } else if (
            parent.getLoginUserType().equals(
                DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT
            )
        ) {
            tabs = parent.getPermittedTabs(
                parent.getLoginUserType(),
                parent.getCurrentAssistant(),
                this.bundle
            );
        }

        LOG.debug("Adding {} tabs to menu", tabs.size());
        while (!tabs.isEmpty()) {
            TabPaneEntry entry = tabs.poll();
            LOG.debug("Adding tab {}", entry.getTitle());

            addTab(entry);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.debug("##########1");
        this.bundle = resources;

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
        return this.parentController;
    }

    @Override
    public void initializeNew() {

    }

    @Override
    public void setParentController(MainController controller) {
        LOG.debug("Initialized TabPaneParentController");
        this.parentController = controller;
    }
}
