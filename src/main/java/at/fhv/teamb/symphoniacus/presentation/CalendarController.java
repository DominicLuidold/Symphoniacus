package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.MusicianManager;
import at.fhv.teamb.symphoniacus.application.SectionMonthlyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.BrutalCalendarSkin;
import at.fhv.teamb.symphoniacus.presentation.internal.PublishDutyRosterEvent;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.CalendarView;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * This controller is responsible for handling all CalendarFX related actions such as
 * creating Calendar {@link Entry} objects, filling a {@link Calendar} and preparing it
 * for display.
 *
 * @author Dominic Luidold
 */
public class CalendarController implements Initializable, Controllable {
    /**
     * Default interval start date represents {@link LocalDate#now()}.
     */
    private static final LocalDate DEFAULT_INTERVAL_START = LocalDate.now().minusMonths(2);

    /**
     * Default interval end date represents {@link #DEFAULT_INTERVAL_START} plus 13 days.
     */
    private static final LocalDate DEFAULT_INTERVAL_END = DEFAULT_INTERVAL_START.plusDays(13);

    /**
     * Extended interval end date represents {@link #DEFAULT_INTERVAL_START} plus one month.
     */
    private static final LocalDate EXTENDED_INTERVAL_END = DEFAULT_INTERVAL_START.plusMonths(4);

    private static final Logger LOG = LogManager.getLogger(CalendarController.class);

    private DutyScheduleController dutyScheduleController;

    private Section section;

    @FXML
    private CalendarView calendarView;

    @FXML
    private AnchorPane dutySchedule;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();

        // TODO - Temporarily used until proper login is introduced
        Optional<UserEntity> user = new LoginManager().login("vaubou", "eItFAJSb");
        Optional<MusicianEntity> musician = new MusicianManager().loadMusician(user.get());
        this.section = new Section(musician.get().getSection());

        // Tell CalendarFX to use custom skin
        this.calendarView.setSkin(new BrutalCalendarSkin(this.calendarView));

        // Event handler for clicking the "Forward" button
        this.calendarView.addEventHandler(
            PublishDutyRosterEvent.PUBLISH_DUTY_ROSTER_EVENT_EVENT_TYPE,
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
                    resources.getString("tab.duty.schedule.forward.month"),
                    monthIcon
                );
                monthLabel.setStyle("-fx-font-weight: bold");
                monthLabel.setFont(f);
                Label selectedLabel = new Label(
                    resources.getString("tab.duty.schedule.forward.selected"),
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
                dialog.setTitle(resources.getString("tab.duty.schedule.forward.title"));

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText(resources.getString("tab.duty.schedule.forward.button"));

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
                                .title(resources.getString(
                                    "notification.schedule.forward.already.title")
                                )
                                .text(
                                    MessageFormat.format(
                                        resources.getString(
                                            "notification.schedule.forward.already.message"
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
                                    .title(resources.getString(
                                        "notification.schedule.forward.fail.title")
                                    )
                                    .text(
                                        MessageFormat.format(
                                            resources.getString(
                                                "notification.schedule.forward.fail.message"
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
                                    .title(resources.getString(
                                        "notification.schedule.forward.ok.title")
                                    )
                                    .text(
                                        MessageFormat.format(
                                            resources.getString(
                                                "notification.schedule.forward.ok.message"
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
                                        + resources.getString("tab.duty.schedule.forward.forwarded")
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

        // Prepare calendar
        this.calendarView.getCalendarSources().setAll(
            prepareCalendarSource(
                resources.getString("domain.section.sections"),
                this.section.getEntity()
            )
        );

        this.calendarView.addEventHandler(LoadEvent.LOAD, event -> {
            LOG.debug("Calendar Load Event, loading");
            LOG.debug("TODO: Would add lazy loading strategy here");
        });

        // Hide calendarView by clicking on Duty and load new Window for DutyScheduleView.
        this.calendarView.setEntryDetailsCallback(
            param -> {
                if (param.getEntry() instanceof Entry) {
                    Entry<Duty> entry = (Entry<Duty>) param.getEntry();
                    MasterController mc = MasterController.getInstance();

                    if (mc.get("CalendarController") instanceof CalendarController) {
                        CalendarController cc = (CalendarController) mc.get("CalendarController");
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
    }

    /**
     * Prepares a {@link CalendarSource} by creating a {@link Calendar} and subsequently filling
     * it with {@link Entry} objects.
     *
     * @param name    Name of the CalendarSource
     * @param section The section to use
     * @return A CalendarSource containing a Calendar and Entries
     */
    public CalendarSource prepareCalendarSource(String name, SectionEntity section) {
        CalendarSource calendarSource = new CalendarSource(name);
        Calendar calendar = createCalendar(section);
        fillCalendar(calendar, section);
        calendarSource.getCalendars().add(calendar);
        return calendarSource;
    }

    /**
     * Creates a {@link Calendar} based on specified {@link SectionEntity}.
     *
     * <p>The calendar will have a {@link Calendar.Style} assigned and will be marked as
     * {@code readOnly}.
     *
     * @param section The section to use
     * @return A Calendar
     */
    public Calendar createCalendar(SectionEntity section) {
        Calendar calendar = new Calendar(section.getDescription());
        calendar.setShortName(section.getSectionShortcut());
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE1);
        return calendar;
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects.
     *
     * @param calendar The calendar to fill
     * @param entries  A List of Entries
     */
    public void fillCalendar(Calendar calendar, List<Entry<Duty>> entries) {
        for (Entry<Duty> entry : entries) {
            entry.setCalendar(calendar);
        }
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects based on specified {@link SectionEntity}.
     *
     * <p>Having no interval defined, the default interval start and end date (13 days from
     * {@link LocalDate#now()} are getting used.
     *
     * @param calendar The calendar to fill
     * @param section  The section used to determine required {@link DutyEntity} objects
     * @see #DEFAULT_INTERVAL_START
     * @see #DEFAULT_INTERVAL_END
     */
    public void fillCalendar(Calendar calendar, SectionEntity section) {
        fillCalendar(calendar, createDutyCalendarEntries(
            new DutyManager().findAllInRangeWithSection(
                section,
                DEFAULT_INTERVAL_START,
                EXTENDED_INTERVAL_END // TODO - Temporarily used until lazy loading is introduced
            )
        ));
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects based on supplied {@link SectionEntity}
     * and dates.
     *
     * @param calendar  The calendar to fill
     * @param section   The section used to determine which {@link DutyEntity} objects
     * @param startDate Start date of the desired interval
     * @param endDate   End date of the desired interval
     */
    public void fillCalendar(
        Calendar calendar,
        SectionEntity section,
        LocalDate startDate,
        LocalDate endDate
    ) {
        fillCalendar(calendar, createDutyCalendarEntries(
            new DutyManager().findAllInRangeWithSection(
                section,
                startDate,
                endDate
            )
        ));
    }

    /**
     * Returns a list of CalendarFX {@link Entry} objects based on {@link DutyEntity} objects.
     *
     * @param duties A List of Duties
     * @return A list of Entries
     */
    public List<Entry<Duty>> createDutyCalendarEntries(List<Duty> duties) {
        List<Entry<Duty>> calendarEntries = new LinkedList<>();
        for (Duty duty : duties) {
            Interval interval = new Interval(
                duty.getEntity().getStart().toLocalDate(),
                duty.getEntity().getStart().toLocalTime(),
                duty.getEntity().getEnd().toLocalDate(),
                duty.getEntity().getEnd().toLocalTime()
            );
            Entry<Duty> entry = new Entry<>(duty.getTitle(), interval);
            entry.setUserObject(duty);
            calendarEntries.add(entry);
        }
        return calendarEntries;
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
}
