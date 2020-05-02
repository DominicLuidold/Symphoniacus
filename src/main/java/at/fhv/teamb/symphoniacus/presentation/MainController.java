package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.AdministrativeAssistantManager;
import at.fhv.teamb.symphoniacus.application.MusicianManager;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;
import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
    private Musician currentMusician;
    private AdministrativeAssistant currentAssistant;
    private MusicianManager musicianManager;
    private AdministrativeAssistantManager administrativeManager;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.debug("####000");
        this.userHeaderMenuController.setParentController(this);
        this.tabPaneController.setParentController(this);
        this.bundle = resources;
        LOG.debug("Initialized MainController");
    }

    /**
     * Finds out if the currently logged-in user is a {@link Musician} or an
     * {@link AdministrativeAssistant} and sets his Domainobject as attribute in this class.
     *
     * @param user Current login user
     */
    public void setLoginUser(User user) {
        this.currentUser = user;

        if (this.currentUser.getType().equals(DomainUserType.DOMAIN_MUSICIAN)) {
            if (musicianManager == null) {
                musicianManager = new MusicianManager();
            }
            Optional<Musician> musician = musicianManager
                .loadMusician(this.currentUser.getUserEntity());

            if (musician.isPresent()) {
                LOG.debug("Musician successfully loaded");
                this.currentMusician = musician.get();
            } else {
                LOG.error("Cannot load Login Musician");
            }
        }
        if (this.currentUser.getType()
            .equals(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT)) {
            if (administrativeManager == null) {
                administrativeManager = new AdministrativeAssistantManager();
            }
            Optional<AdministrativeAssistant> administrativeAssistant =
                administrativeManager.loadAdministrativeAssistant(
                    this.currentUser.getUserEntity().getUserId()
                );
            if (administrativeAssistant.isPresent()) {
                LOG.debug("AdministrativeAssistant successfully loaded");
                this.currentAssistant = administrativeAssistant.get();
            } else {
                LOG.debug("Cannot load Login AdministrativeAssistant");
            }
        }

        try {
            this.tabPaneController.initializeTabMenu();
        } catch (IOException e) {
            LOG.error("Cannot build TabPane", e);
        }
    }

    public DomainUserType getLoginUserType() {
        return currentUser.getType();
    }

    public Musician getCurrentMusician() {
        return currentMusician;
    }

    public AdministrativeAssistant getCurrentAssistant() {
        return currentAssistant;
    }

    protected List<TabPaneEntry> getPermittedTabs(
        DomainUserType type,
        Musician m
    ) {
        return this.getPermittedTabs(type, m, null);
    }

    protected List<TabPaneEntry> getPermittedTabs(
        DomainUserType type,
        AdministrativeAssistant assistant
    ) {
        return this.getPermittedTabs(type, null, assistant);
    }

    private List<TabPaneEntry> getPermittedTabs(
        DomainUserType type,
        Musician m,
        AdministrativeAssistant assistant
    ) {
        List<TabPaneEntry> result = new LinkedList<>();
        if (m == null && assistant == null) {
            LOG.error("Cannot getPermittedTabs for null users");
            return result;
        }

        // Musician
        if (m != null && assistant == null) {
            LOG.debug("Getting permittedTabs for Musician");
            LOG.debug("No default view for Musician atm");

            for (MusicianRole role : m.getEntity().getMusicianRoles()) {
                if (role.getDescription().equals(MusicianRoleType.DUTY_SCHEDULER)) {
                    result.add(
                        new TabPaneEntry(
                            this.bundle.getString("menu.tab.duty.roster.title"),
                            "/view/dutySchedulerCalendar.fxml"
                        )
                    );
                    result.add(
                        new TabPaneEntry(
                            this.bundle.getString("menu.tab.duty.roster.title"),
                            "/view/dutySchedule.fxml"
                        )
                    );
                }
            }
            // Organizational Officer
        } else if (assistant != null && m == null) {
            LOG.debug("Getting permittedTabs for Administrative Assistant");
            result.add(
                new TabPaneEntry(
                    this.bundle.getString("menu.tab.duty.roster.title"),
                    "/view/organizationalOfficerCalendarView.fxml"
                )
            );
        }

        return result;
    }

}
