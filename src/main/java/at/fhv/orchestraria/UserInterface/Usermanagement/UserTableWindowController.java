package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.UserInterface.Login.LoginWindow;
import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import at.fhv.orchestraria.UserInterface.MainWindow.MainWindow;
import at.fhv.orchestraria.UserInterface.Roster.RosterWindow;
import at.fhv.orchestraria.application.UserManagementController;
import at.fhv.orchestraria.domain.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import at.fhv.teamb.symphoniacus.presentation.TabPaneController;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserTableWindowController implements Parentable<TabPaneController> {
    public TreeItem newUserRow = new TreeItem(new UserWrapper("Add new User", "", "", "", ""));
    private UserTableWindowController controller = this;
    private final static Logger LOGGER = Logger.getLogger(UserTableWindowController.class.getName());
    private UserManagementController uManagementController;
    private  LinkedList<TreeItem<UserWrapper>> removedItems = new LinkedList<>();

    private TabPaneController parentController;
  //TODO remove this line; created for testing purposes
    public void bestfunction(){
        System.out.println("This is the best function ever!");
    }

    @FXML
    private Label _loggedInUserName;

    @FXML
    private ToggleButton home_navBttn;

    @FXML
    private ToggleGroup navigation1;

    @FXML
    private ToggleButton roster_navBttn;

    @FXML
    private ToggleButton points_navBttn;

    @FXML
    private ToggleButton wishes_navBttn;

    @FXML
    private ToggleButton substitutes_navBttn;

    @FXML
    private ToggleButton profile_navBttn;

    @FXML
    private ToggleButton logout_navBttn;

    @FXML
    private ToggleGroup navigation;

    @FXML
    private JFXTextField input;

    @FXML
    private JFXTreeTableView<UserWrapper> treeTableView;

    @FXML
    private TreeTableColumn<UserWrapper, String> fnameCol;

    @FXML
    private TreeTableColumn<UserWrapper, String> lnameCol;

    @FXML
    private TreeTableColumn<UserWrapper, String> sectionCol;

    @FXML
    private TreeTableColumn<UserWrapper, String> roleCol;

    @FXML
    private TreeTableColumn<UserWrapper, String> contractCol;

    @FXML
    private JFXProgressBar JFXloadingBar;

    @FXML
    void changeScreenDutyRoster(ActionEvent event) {

    }

    @FXML
    void notAvailableAlert(ActionEvent event) {

    }
    //Views

    @FXML
    void notAvailableAlert(javafx.event.ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not ready yet");
        alert.setHeaderText(null);
        alert.setContentText("Sorry still in development!");

        alert.showAndWait();
    }

    public MainWindow _mainWindow;


    public void setMain(MainWindow main) {
        _mainWindow = main;

    }


    /**
     * @param event is the action of clicking the duty roster button
     */
    public void changeScreenDutyRoster(javafx.event.ActionEvent event) throws Exception {
        RosterWindow rw = new RosterWindow();

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        rw.start(window);
    }

    public void init() {
        //setLoggedInUserName(LoginWindowController.getLoggedInUser());
        uManagementController = new UserManagementController();
        JFXloadingBar.setVisible(true);
        //setLoggedInUserName(LoginWindowController.getLoggedInUser());
        loadMusiciansIntoTable();


        treeTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (event.getClickCount() == 2) {
                    boolean newUser;
                    if (treeTableView.getSelectionModel().getSelectedItem().getChildren().size() > 0) {
                        newUser = true;
                    } else {
                        newUser = false;
                    }
                    UserEdit uEWindow = new UserEdit();
                    Stage window = new Stage();
                    try {
                        uEWindow.setParameter(new UserEditWindowController(), treeTableView.getSelectionModel().getSelectedItem().getValue().getUser(), treeTableView.getSelectionModel().getFocusedIndex(), controller, newUser);
                        uEWindow.start(window);
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, "Exception ", e);
                    }
                }
            }
        });
        JFXloadingBar.setVisible(false);
        treeTableView.scrollTo(0);
        searchFunctionality();
    }


    public void loadMusiciansIntoTable() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fnameCol.setCellValueFactory(new TreeItemPropertyValueFactory<UserWrapper, String>("fname"));
                lnameCol.setCellValueFactory(new TreeItemPropertyValueFactory<UserWrapper, String>("lname"));
                sectionCol.setCellValueFactory(new TreeItemPropertyValueFactory<UserWrapper, String>("section"));
                roleCol.setCellValueFactory(new TreeItemPropertyValueFactory<UserWrapper, String>("role"));
                contractCol.setCellValueFactory(new TreeItemPropertyValueFactory<UserWrapper, String>("contractEnd"));


                treeTableView.setRoot(newUserRow);


                //PseudoClass to identify first row of the table view to set style
                final PseudoClass firstRowClass = PseudoClass.getPseudoClass("first-row");

                treeTableView.setRowFactory(treeTable -> {
                    TreeTableRow row = new TreeTableRow<>();
                    row.treeItemProperty().addListener((ov, oldTreeItem, newTreeItem) ->
                            row.pseudoClassStateChanged(firstRowClass, newTreeItem == treeTable.getRoot()));
                    return row;
                });

                newUserRow.setExpanded(true);
                newUserRow.expandedProperty().addListener(observable -> {
                    if (!newUserRow.isExpanded()) {
                        newUserRow.setExpanded(true);
                    }
                });

            }
        });

        for (IUserEntity ue : uManagementController.getUsers()) {
            if (ue.getUserId() != MusicianEntity.EXTERNAL_MUSICIAN_ID) {
                TreeItem ti = new TreeItem(new UserWrapper(ue));
                newUserRow.getChildren().add(ti);
            }
        }
    }

    private void searchFunctionality(){
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int i = 1;
                newValue = newValue.toLowerCase();
                treeTableView.getRoot().getChildren().addAll(removedItems);
                removedItems.clear();
                while(treeTableView.getTreeItem(i)!= null) {
                    if(!(treeTableView.getTreeItem(i).getValue().getFname().toLowerCase().contains(newValue)||treeTableView.getTreeItem(i).getValue().getLname().toLowerCase().contains(newValue)||treeTableView.getTreeItem(i).getValue().getRole().toLowerCase().contains(newValue)||treeTableView.getTreeItem(i).getValue().getSection().toLowerCase().contains(newValue)||treeTableView.getTreeItem(i).getValue().getContractEnd().toLowerCase().contains(newValue))) {
                        removedItems.add(treeTableView.getTreeItem(i));
                        treeTableView.getRoot().getChildren().remove(treeTableView.getTreeItem(i));
                    }
                    else {
                        i++;
                    }
                }
            }
        });
    }

    public void updateRow(int index, IUserEntity ue) {
        treeTableView.getSelectionModel().getModelItem(index).setValue(new UserWrapper(ue));
    }

    public void insertNewRow(IUserEntity ue) {
        newUserRow.getChildren().add(0, new TreeItem(new UserWrapper(ue)));
    }

    /**
     * Sets this controller's parent controller.
     *
     * @param controller The controller to be set as parent
     */
    @Override
    public void setParentController(TabPaneController controller) {
         this.parentController = controller;
    }

    /**
     * Returns this controller's parent controller.
     *
     * @return Parent controller
     */
    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    /**
     * Calls the controller initialization AFTER the parent controller has been set by
     * {@link TabPaneController}.
     */
    @Override
    public void initializeWithParent() {
        // No implementation needed in this class.
        this.init();
        System.out.println(("Initialized TabPaneController with parent"));
    }

    public class UserWrapper extends RecursiveTreeObject<UserWrapper> {
        private String fname;
        private String lname;
        private String section;
        private String role;
        private String contractEnd;
        private boolean isBlankForUserCreation = false;
        private IUserEntity user;

        public UserWrapper(String fname, String lname, String section, String role, String contractEnd) {
            this.fname = fname;
            this.lname = lname;
            this.section = section;
            this.role = role;
            this.contractEnd = contractEnd;
            this.isBlankForUserCreation = true;
        }

        public UserWrapper(IUserEntity ue) {
            user = ue;
            fname = ue.getFirstName();
            lname = ue.getLastName();
            if (ue.getMusician() == null) {
                section = "-";
                if (ue.getAdministrativeAssistants() != null) {
                    StringBuilder sb = new StringBuilder();
                    for (IAdministrativeAssistantEntity ass : ue.getAdministrativeAssistants()) {
                        sb.append(ass.getDescription());
                        sb.append(" | ");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    sb.deleteCharAt(sb.length()-1);
                    sb.deleteCharAt(sb.length()-1);
                    role = sb.toString();
                } else {
                    role = "-";
                }
                contractEnd = "-";
            } else {
                if (ue.getMusician().getSection() != null) {
                    section = ue.getMusician().getSection().getDescription();
                } else {
                    section = "-";
                }
                role = "-";
                for (IMusicianRole mrme : ue.getMusician().getMusicianRoles()) {
                    role = mrme.getDescription().toString();
                }
                contractEnd = "-";
                for (IContractualObligationEntity coe : ue.getMusician().getContractualObligations()) {
                    contractEnd = coe.getEndDate().toString();
                }
            }
        }

        public StringProperty fnameProperty() {
            return new SimpleStringProperty(fname);
        }

        public StringProperty lnameProperty() {
            return new SimpleStringProperty(lname);
        }

        public StringProperty sectionProperty() {
            return new SimpleStringProperty(section);
        }

        public StringProperty roleProperty() {
            return new SimpleStringProperty(role);
        }

        public StringProperty contractEndProperty() {
            return new SimpleStringProperty(contractEnd);
        }

        public String getFname() {
            return fname;
        }

        public String getLname() {
            return lname;
        }

        public String getSection() {
            return section;
        }

        public String getRole() {
            return role;
        }

        public String getContractEnd() {
            return contractEnd;
        }

        public IUserEntity getUser() {
            return user;
        }
    }

    public void logout(javafx.event.ActionEvent event) throws Exception {
        LoginWindowController.logout();

        try {
            LoginWindow lw = new LoginWindow();
            Stage homeWindowDecorated = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //make next undecorated
            Stage undecoratedWindow = new Stage();
            undecoratedWindow.initStyle(StageStyle.UNDECORATED);
            Scene scene = homeWindowDecorated.getScene();
            homeWindowDecorated.setScene(null);
            undecoratedWindow.setScene(scene);
            undecoratedWindow.hide();
            homeWindowDecorated.hide();
            lw.start(undecoratedWindow);
        }catch(Exception e){
            LOGGER.log(Level.INFO, "Exception ", e);
        }
    }

    /*
    public void setLoggedInUserName(IUser user){
        _loggedInUserName.setText(user.getFirstName() + " " + user.getLastName());
    }

     */
}
