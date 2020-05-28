package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.UserInterface.Login.LoginWindow;
import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import at.fhv.orchestraria.UserInterface.MainWindow.MainWindow;
import at.fhv.orchestraria.application.DutyAssignmentController;
import at.fhv.orchestraria.domain.Imodel.IDuty;
import at.fhv.orchestraria.domain.Imodel.IUser;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.adapter.MusicianAdapter;
import at.fhv.teamb.symphoniacus.presentation.TabPaneController;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayViewBase;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RosterWindowController implements Parentable<TabPaneController> {
    //Logger
    private final static Logger LOGGER = Logger.getLogger(RosterWindowController.class.getName());
    //Views

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
    private ToggleGroup navigation11;

    @FXML
    private ToggleButton dynamic_navBttn;

    @FXML
    private ToggleButton substitutes_navBttn;

    @FXML
    private ToggleButton profile_navBttn;

    @FXML
    private ToggleButton logout_navBttn;

    @FXML
    private ToggleGroup navigation;

    @FXML private CalendarView cview;

    private DutyAssignmentController _dAssController;

    public RosterWindow _rosterWindow;

    private PopOverDutySchedulerController _popOverDutySchedulerController;

    private HashMap<IDuty, OrchestraEntry> _assignedDutyMapping = new HashMap<>();

    private HashMap<IDuty, OrchestraEntry> _unassignedDutyMapping = new HashMap<>();

    private AssignmentRosterThread _assignmentRosterThread;

    private RosterThread _rosterThread;

    private TabPaneController parentController;

    public synchronized void setMain(RosterWindow main, boolean isAssignment) {

        if(isAssignment){
            _rosterThread = new AssignmentRosterThread(this,cview);
        }else{
            if (this.parentController.getParentController().getLoginUserType().equals(DomainUserType.DOMAIN_MUSICIAN)) {
                _rosterThread = new ViewRosterThread(
                    cview,
                    new MusicianAdapter(
                        this.parentController.getParentController().getCurrentMusician().getEntity()
                    )
                );
            } else {
                // If Team C had a logger, we would have used it.. lol
                return;
            }
        }

        /* Not used in integration
            if(LoginWindowController.getLoggedInUser().getMusician().isDutyScheduler()){
                dynamic_navBttn.setText("Assignment Roster");
                dynamic_navBttn.setPrefWidth(120);
            }
         */

        cview.showWeekPage();
        cview.getWeekPage().getDetailedWeekView().setHourHeight(40);
        cview.getWeekPage().getDetailedWeekView().setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_HEIGHT);
        cview.weekFieldsProperty().setValue(WeekFields.of(DayOfWeek.MONDAY,4));

        //Remove Standard Calendar
        cview.getCalendarSources().remove(0);

        Thread roster = new Thread(_rosterThread);
        roster.start();

        cview.getCalendarSources().addAll(_rosterThread.getSources());

        runTimeUpdateThread();
        _rosterWindow = main;

        // select the duty roster button
        /* Not used in integration
            if(isAssignment){
                dynamic_navBttn.setSelected(true);
            }else{
                roster_navBttn.setSelected(true);
            }

            setLoggedInUserName(LoginWindowController.getLoggedInUser());
         */
    }


    /**
     * Activates the update of the time display in the CalendarView
     */
    private void runTimeUpdateThread() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        cview.setToday(LocalDate.now());
                        cview.setTime(LocalTime.now());
                    });
                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.log(Level.INFO, "interrupted" );
                    }
                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }


    /**
     * Redirects the logged in user to the page that fits the button description depending on the role either the
     * "Wishes" page or the Assignment Roster.
     * @param event The click event of the button.
     * @throws Exception One of the windows cannot be started.
     */
    @FXML
    void redirectByMusicianRole(ActionEvent event) throws Exception {
        if(LoginWindowController.getLoggedInUser().getMusician().isDutyScheduler()){
            startRosterWindow(event, true);
        }else{
            notAvailableAlert(event);
        }
    }


    private void startRosterWindow(ActionEvent event, boolean isAssignment) throws Exception{
        RosterWindow rw = new RosterWindow();
        rw.setMode(isAssignment);
        //This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        rw.start(window);
    }

    @FXML
    private void loadMainPage(ActionEvent event) throws Exception {
        MainWindow mw = new MainWindow();

        //This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        mw.start(window);
    }


    @FXML
    void notAvailableAlert(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not ready yet");
        alert.setHeaderText(null);
        alert.setContentText("Sorry still in development!");

        alert.showAndWait();
    }

    @FXML
    public void changeScreenDutyRoster(ActionEvent event) throws Exception {
        startRosterWindow(event, false);
    }

    public void logout(ActionEvent event) throws Exception {
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

    public void setLoggedInUserName(IUser user){
        _loggedInUserName.setText(user.getFirstName() + " " + user.getLastName());
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    @Override
    public void initializeWithParent() {
       this.setMain(null, false);
    }
}
