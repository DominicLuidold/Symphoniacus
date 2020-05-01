package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.MusicianManager;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.dao.AdministrativeAssistantDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userHeaderMenuController.setParentController(this);
        this.tabPaneController.setParentController(this);
        LOG.debug("Initialized MainController");
        LOG.debug(tabPaneController);
    }

    /**
     * Finds out if the currently logged-in user is a {@link Musician} or an
     * {@link AdministrativeAssistant} and sets his Domainobject as attribute in this class.
     * @param user Current login user
     */
    public void setLoginUser(User user) {
        this.currentUser = user;

        if (this.currentUser.getType().equals(DomainUserType.DOMAIN_MUSICIAN)) {
            MusicianManager mm = new MusicianManager();
            Optional<MusicianEntity> musician = mm.loadMusician(this.currentUser.getUserEntity());
            if (musician.isPresent()) {
                this.currentMusician = new Musician(musician.get());
            }
        }
        if (this.currentUser.getType()
            .equals(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT)) {
            AdministrativeAssistantDao aad = new AdministrativeAssistantDao();
            Optional<AdministrativeAssistantEntity> administrativeAssistantEntity =
                aad.find(this.currentUser.getUserEntity().getUserId());
            if (administrativeAssistantEntity.isPresent()) {
                this.currentAssistant =
                    new AdministrativeAssistant(administrativeAssistantEntity.get());
            }
        }
    }
}
