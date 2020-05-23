package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import at.fhv.teamb.symphoniacus.presentation.TabPaneController;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import java.util.Optional;
import javafx.application.Application;
import javafx.stage.Stage;

public class UserEdit extends Application implements Parentable<TabPaneController> {


    private UserEditWindowController _userEditWindowController;
    private IUserEntity _ue;
    private int listviewindex;
    private UserTableWindowController _parentTreeTable;
    private boolean isNewUser;

    private TabPaneController parentController;

    public void setParameter(UserEditWindowController userEditWindowController, IUserEntity ue,
                             int listindex, UserTableWindowController parentTreeTable,
                             boolean _isNewUser) {
        _ue = ue;
        listviewindex = listindex;
        _parentTreeTable = parentTreeTable;
        isNewUser = _isNewUser;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) throws Exception {
        /*
        Not used in integration
         */
        //Locale.setDefault(Locale.ENGLISH);
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/team-c/UserEdit.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Edit User");

        primaryStage.getIcons().add(new Image(UserTable.class.getResourceAsStream(
            "/images/team-c/orchestraria_icon.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setY(5);
        double width = 738;
        primaryStage.setX((primScreenBounds.getWidth() - width) / 2);
         */

        Optional<Parentable<?>> controller = this.parentController.addTab(TabPaneEntry.USER_EDIT);
        if (controller.isPresent()) {
            Parentable<TabPaneController> con = (Parentable<TabPaneController>) controller.get();
            _userEditWindowController = (UserEditWindowController)con;
        }

        //_userEditWindowController = parentController.getController();

        _userEditWindowController.setParameter(_ue, listviewindex, _parentTreeTable, isNewUser);
        _userEditWindowController.init();
        //primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
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

    }
}

