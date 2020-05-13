package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.SectionMonthlyScheduleManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.presentation.internal.CustomCalendarButtonEvent;
import at.fhv.teamb.symphoniacus.presentation.internal.popover.CustomDutyPopoverNode;
import at.fhv.teamb.symphoniacus.presentation.internal.skin.DutySchedulerCalendarSkin;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.FindAllInRangeWithSectionTask;
import com.calendarfx.model.Calendar;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * This controller is responsible for handling CalendarFX related actions related
 * to the duty scheduler and their tasks.
 *
 * @author Theresa Gierer
 * @author Dominic Luidold
 */
public class DutySchedulerCalendarController extends CalendarController implements Controllable {
    private static final Logger LOG = LogManager.getLogger(DutySchedulerCalendarController.class);
    private ResourceBundle resources;
    private SectionDto section;
    private TabPaneController parentController;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private DutyScheduleController dutyScheduleController;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        this.dutyScheduleController.setParentController(this);
        MainController mainController = this.getParentController().getParentController();
        DomainUserType loginUserType = mainController.getLoginUserType();
        Musician musician = null;
        if (loginUserType.equals(DomainUserType.DOMAIN_MUSICIAN)) {
            LOG.info("Current user type is Musician");
            musician = mainController.getCurrentMusician();
        } else {
            LOG.error("Current user type is unsupported for this view");
            return;
        }
        SectionDto.SectionDtoBuilder sectionDtoBuilder = new SectionDto
            .SectionDtoBuilder(musician.getSection().getEntity().getSectionId());
        sectionDtoBuilder
            .withSectionShortcut(musician.getEntity().getSection().getSectionShortcut());
        sectionDtoBuilder.withDescription(musician.getEntity().getSection().getDescription());
        sectionDtoBuilder.withSectionMonthlySchedules(
            musician.getEntity().getSection().getSectionMonthlySchedules());
        sectionDtoBuilder.withMusicians(musician.getEntity().getSection().getMusicians());
        sectionDtoBuilder.withDutyPositions(musician.getEntity().getSection().getDutyPositions());
        sectionDtoBuilder.withSectionInstrumentations(
            musician.getSection().getEntity().getSectionInstrumentations());
        this.section = sectionDtoBuilder.build();

        // Tell CalendarFX to use custom skin
        this.setCalendarSkin();

        // Hide calendarView by clicking on Duty and load new Window for DutyScheduleView.
        this.setEntryDetailsCallback();

        // Event handler for clicking the "Forward" button
        this.addForwardButtonHandler();

        // Fetch duties from database
        FindAllInRangeWithSectionTask task =
            this.loadDuties(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);
        new Thread(task).start();

        task.setOnSucceeded(event -> {
            // Create calendar
            Calendar calendar = this.createCalendar(
                this.section.getDescription(),
                this.section.getSectionShortcut(),
                true
            );

            // Fill calendar
            this.fillCalendar(calendar, task.getValue());

            // Make Calendar ready to display
            this.calendarView.getCalendarSources().setAll(
                this.prepareCalendarSource(
                    resources.getString("domain.section.sections"),
                    calendar
                )
            );

            LOG.debug("Initialized DutySchedulerCalendarController with parent");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.registerController();

        this.calendarView.setEntryDetailsPopOverContentCallback(param ->
            new CustomDutyPopoverNode(
                param.getPopOver(),
                param.getDateControl(),
                param.getNode(),
                param.getEntry(),
                this.section
            )
        );
        LOG.debug("Initialized DutySchedulerCalendarController");
    }

    /**
     * Adds logic to handle the {@link CustomCalendarButtonEvent#FORWARD_DUTY_ROSTER_EVENT}.
     */
    private void addForwardButtonHandler() {
        this.calendarView.addEventHandler(
            CustomCalendarButtonEvent.FORWARD_DUTY_ROSTER_EVENT,
            event -> {
                LOG.debug("Publish duty roster event callback!");

                // Get section monthly schedules from backend
                SectionMonthlyScheduleManager smsManager = new SectionMonthlyScheduleManager();
                Set<SectionMonthlySchedule> sectionMonthlySchedules =
                    smsManager.getSectionMonthlySchedules(this.section, Year.now());

                // Create custom icons
                FontIcon monthIcon = new FontIcon(FontAwesome.CALENDAR_PLUS_O);
                monthIcon.getStyleClass().addAll("button-icon");
                FontIcon selectedIcon = new FontIcon(FontAwesome.CHECK_SQUARE_O);
                selectedIcon.getStyleClass().addAll("button-icon");

                // Fill list selection with content
                ListSelectionView<SectionMonthlySchedule> listSelectionView =
                    new ListSelectionView<>();
                listSelectionView.getSourceItems().addAll(sectionMonthlySchedules);

                // Define custom list view labels
                Font f = new Font(14);
                Label monthLabel = new Label(
                    this.resources.getString("tab.duty.schedule.forward.month"),
                    monthIcon
                );
                monthLabel.setStyle("-fx-font-weight: bold");
                monthLabel.setFont(f);
                Label selectedLabel = new Label(
                    this.resources.getString("tab.duty.schedule.forward.selected"),
                    selectedIcon
                );
                selectedLabel.setStyle("-fx-font-weight: bold");
                selectedLabel.setFont(f);

                // Prepare selection window
                listSelectionView.sourceHeaderProperty().set(monthLabel);
                listSelectionView.targetHeaderProperty().set(selectedLabel);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(this.calendarView.getParent().getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                dialog.getDialogPane().setContent(listSelectionView);
                dialog.setResizable(true);
                dialog.getDialogPane().setPrefWidth(600);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle(this.resources.getString("tab.duty.schedule.forward.title"));

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText(this.resources.getString("tab.duty.schedule.forward.button"));

                // Logic for pressing forward button
                okButton.setOnAction(event1 -> {
                    ObservableList<SectionMonthlySchedule> list =
                        listSelectionView.getTargetItems();

                    // Prepare notification
                    Notifications notification = Notifications
                        .create()
                        .position(Pos.CENTER)
                        .hideAfter(new Duration(7000));

                    // Show appropriate message based on sms publish state
                    for (SectionMonthlySchedule sms : list) {
                        String month = Month.of(sms.getEntity().getMonthlySchedule().getMonth())
                            .getDisplayName(TextStyle.FULL, Locale.US);

                        if (sms.getPublishState()
                            .equals(
                                SectionMonthlySchedule
                                    .PublishState.READY_FOR_ORGANISATION_MANAGER
                            )
                        ) {
                            // Section monthly schedule already forwarded - show error
                            notification
                                .owner(this.calendarView.getParent().getScene().getWindow())
                                .title(
                                    this.resources.getString(
                                        "notif.schedule.forward.already.title"
                                    )
                                )
                                .text(
                                    MessageFormat.format(
                                        this.resources.getString(
                                            "notif.schedule.forward.already.message"
                                        ),
                                        month
                                    )
                                )
                                .showError();
                        } else if (!sms.getPublishState()
                            .equals(
                                SectionMonthlySchedule
                                    .PublishState.READY_FOR_ORGANISATION_MANAGER
                            )
                        ) {
                            // Section monthly schedule not yet forwarded
                            LOG.debug(
                                "Forward to Organisation Manager "
                                    + "SectionMonthlySchedule ID: {}",
                                sms.getEntity().getSectionMonthlyScheduleId());

                            // Delegate forward to backend
                            smsManager.makeAvailableForOrganisationManager(sms);

                            if (!sms.getPublishState()
                                .equals(
                                    SectionMonthlySchedule
                                        .PublishState.READY_FOR_ORGANISATION_MANAGER
                                )
                            ) {
                                // Forward failed - show error
                                LOG.debug(
                                    "Forward to Organisation Manager "
                                        + "FAILED for SectionMonthlySchedule "
                                        + "ID: {} ",
                                    sms.getEntity().getSectionMonthlyScheduleId());

                                notification
                                    .owner(this.calendarView.getParent().getScene().getWindow())
                                    .title(
                                        this.resources.getString(
                                            "notif.schedule.forward.fail.title"
                                        )
                                    )
                                    .text(
                                        MessageFormat.format(
                                            this.resources.getString(
                                                "notif.schedule.forward.fail.message"
                                            ),
                                            month
                                        )
                                    )
                                    .showError();
                            } else if (sms.getPublishState()
                                .equals(
                                    SectionMonthlySchedule.PublishState
                                        .READY_FOR_ORGANISATION_MANAGER
                                )
                            ) {
                                // Forward successful - show info
                                notification
                                    .owner(this.calendarView.getParent().getScene().getWindow())
                                    .title(
                                        this.resources.getString(
                                            "notif.schedule.forward.ok.title"
                                        )
                                    )
                                    .text(
                                        MessageFormat.format(
                                            this.resources.getString(
                                                "notif.schedule.forward.ok.message"
                                            ),
                                            month
                                        )
                                    )
                                    .show();
                            }
                        }
                    }
                });

                // Handle items of list selection view
                listSelectionView.setCellFactory(listView -> {
                    ListCell<SectionMonthlySchedule> cell = new ListCell<>() {
                        @Override
                        public void updateItem(SectionMonthlySchedule item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                Month m = Month.of(
                                    item.getEntity().getMonthlySchedule().getMonth()
                                );

                                LOG.debug(
                                    "Is sms for month {} published? {}",
                                    m.getDisplayName(TextStyle.FULL, Locale.US),
                                    item.getEntity().isReadyForOrganisationManager()
                                );
                                if (item.getEntity().isReadyForOrganisationManager()) {
                                    setText(
                                        m.getDisplayName(TextStyle.FULL, Locale.US)
                                            + " "
                                            + resources
                                            .getString("tab.duty.schedule.forward.forwarded")
                                    );
                                    setStyle("-fx-text-fill: #3e681f");
                                    setGraphic(null);
                                    setEditable(false);
                                    setDisabled(true);
                                    setDisable(true);
                                } else {
                                    setText(m.getDisplayName(TextStyle.FULL, Locale.US));
                                    setStyle("-fx-text-fill: #631616");
                                    setGraphic(null);
                                    setEditable(true);
                                    setDisabled(false);
                                    setDisable(false);
                                }
                            }
                        }
                    };
                    cell.setFont(Font.font(14));
                    return cell;
                });
                dialog.show();
            }
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setCalendarSkin() {
        this.calendarView.setSkin(new DutySchedulerCalendarSkin(this.calendarView));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setEntryDetailsCallback() {

        /*
        this.calendarView.setEntryDetailsCallback(
            param -> {
                if (param.getEntry() instanceof Entry) {
                    Entry<Duty> entry = (Entry<Duty>) param.getEntry();
                    MasterController mc = MasterController.getInstance();

                    if (mc.get("CalendarController") instanceof DutySchedulerCalendarController) {
                        DutySchedulerCalendarController cc =
                            (DutySchedulerCalendarController) mc.get("CalendarController");
                        cc.hide();
                    }

                    if (this.dutyScheduleController == null) {
                        if (mc.get("DutyScheduleController") instanceof DutyScheduleController) {
                            this.dutyScheduleController =
                                (DutyScheduleController) mc.get("DutyScheduleController");
                        }
                    }

                    this.dutyScheduleController.setDuty(entry.getUserObject());
                    this.dutyScheduleController.setSection(this.section);
                    this.dutyScheduleController.show();
                    return true;
                }
                LOG.error("Unrecognized Calendar Entry: No Duty found");
                return false;
            }
        );

         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FindAllInRangeWithSectionTask loadDuties(LocalDate start, LocalDate end) {
        return new FindAllInRangeWithSectionTask(
            this.dutyManager,
            this.section,
            start,
            end,
            this.calendarPane
        );
    }

    @Override
    public void registerController() {
        MasterController mc = MasterController.getInstance();
        mc.put("CalendarController", this);
    }

    @Override
    public void show() {
        this.calendarView.setVisible(true);
    }

    @Override
    public void hide() {
        this.calendarView.setVisible(false);
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }
}
