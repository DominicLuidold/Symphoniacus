package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.MusicianManager;
import at.fhv.teamb.symphoniacus.application.SectionMonthlyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.BrutalCalendarSkin;
import at.fhv.teamb.symphoniacus.presentation.internal.PublishDutyRosterEvent;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import impl.com.calendarfx.view.CalendarViewSkin;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
    private static final LocalDate DEFAULT_INTERVAL_START = LocalDate.now();

    /**
     * Default interval end date represents {@link #DEFAULT_INTERVAL_START} plus 13 days.
     */
    private static final LocalDate DEFAULT_INTERVAL_END = DEFAULT_INTERVAL_START.plusDays(13);

    /**
     * Extended interval end date represents {@link #DEFAULT_INTERVAL_START} plus one month.
     */
    private static final LocalDate EXTENDED_INTERVAL_END = DEFAULT_INTERVAL_START.plusMonths(1);

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
        CalendarViewSkin skin = (CalendarViewSkin) this.calendarView.getSkin();
        this.calendarView.setSkin(new BrutalCalendarSkin(this.calendarView));

        this.calendarView.addEventHandler(
            PublishDutyRosterEvent.PUBLISH_DUTY_ROSTER_EVENT_EVENT_TYPE,
            event -> {
                LOG.debug("Publish duty roster event callback!");

                SectionMonthlyScheduleEntity smse = new SectionMonthlyScheduleEntity();
                smse.setSectionMonthlyScheduleId(3);
                SectionMonthlySchedule smss = new SectionMonthlySchedule(smse);
                MonthlyScheduleEntity ms = new MonthlyScheduleEntity();
                ms.setMonthlyScheduleId(5);
                ms.setMonth(6);
                smss.getEntity().setMonthlySchedule(ms);
                smss.setPublishState(SectionMonthlySchedule.PublishState.PUBLISHED);

                SectionMonthlyScheduleManager smsm = new SectionMonthlyScheduleManager();
                Set<SectionMonthlySchedule> sectionMonthlyScheduleSet =
                    smsm.getSectionMonthlySchedules(Year.now());

                //filter to get only current section
                Iterator<SectionMonthlySchedule> itr = sectionMonthlyScheduleSet.iterator();
                while (itr.hasNext()) {
                    if (!itr.next().getEntity().getSection().getSectionId()
                        .equals(this.section.getEntity().getSectionId())) {
                        itr.remove();
                    }
                }


                FontIcon monthIcon = new FontIcon(FontAwesome.CALENDAR_PLUS_O);
                monthIcon.getStyleClass().addAll("button-icon");
                FontIcon selectedIcon = new FontIcon(FontAwesome.CHECK_SQUARE_O);
                selectedIcon.getStyleClass().addAll("button-icon");

                ListSelectionView<SectionMonthlySchedule> listSelectionView =
                    new ListSelectionView();

                listSelectionView.getSourceItems().addAll(sectionMonthlyScheduleSet);
                listSelectionView.setCellFactory(listView -> {
                    ListCell<SectionMonthlySchedule> cell = new ListCell<SectionMonthlySchedule>() {
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

                                if (item.getEntity().isPublished()) {
                                    setText(m.getDisplayName(TextStyle.FULL, Locale.US)
                                        + "Already Published.");
                                    setGraphic(null);
                                    setEditable(false);
                                    setDisabled(true);
                                    setDisable(true);
                                } else {
                                    setText(m.getDisplayName(TextStyle.FULL, Locale.US));
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

                Font f = new Font(14);

                Label monthLabel = new Label("Month", monthIcon);
                monthLabel.setStyle("-fx-font-weight: bold");
                monthLabel.setFont(f);
                Label selectedLabel = new Label("Selected", selectedIcon);
                selectedLabel.setStyle("-fx-font-weight: bold");
                selectedLabel.setFont(f);

                listSelectionView.sourceHeaderProperty().set(monthLabel);
                listSelectionView.targetHeaderProperty().set(selectedLabel);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(this.calendarView.getParent().getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                dialog.getDialogPane().setContent(listSelectionView);
                dialog.setResizable(true);
                dialog.getDialogPane().setPrefWidth(600);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Publish Duty Roster");

                Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

                btn.setOnAction(event1 -> {
                    ObservableList<SectionMonthlySchedule> list =
                        listSelectionView.getTargetItems();
                    for (SectionMonthlySchedule sms : list) {
                        if (!sms.getPublishState()
                            .equals(SectionMonthlySchedule.PublishState.PUBLISHED)) {

                            LOG.debug("Publish SectionMonthlySchedule ID: {}",
                                sms.getEntity().getSectionMonthlyScheduleId());
                            smsm.makeAvailableForOrganisationManager(sms);

                            if (!sms.getPublishState()
                                .equals(SectionMonthlySchedule.PublishState.PUBLISHED)) {
                                LOG.debug("Publish FAILD SectionMonthlySchedule ID: {} ",
                                    sms.getEntity().getSectionMonthlyScheduleId());

                                Notifications.create()
                                    .owner(this.calendarView.getParent().getScene().getWindow())
                                    .title("Publishing Faild")
                                    .text("You cant Publish this yet.")
                                    .position(Pos.CENTER)
                                    .hideAfter(new Duration(3000))
                                    .showError();
                            } else if (sms.getPublishState()
                                .equals(SectionMonthlySchedule.PublishState.PUBLISHED)) {
                                Notifications.create()
                                    .owner(this.calendarView.getParent().getScene().getWindow())
                                    .title("Publishing Successful")
                                    .text("Monthly Schedule is Successfuly published")
                                    .position(Pos.CENTER)
                                    .hideAfter(new Duration(3000))
                                    .show();
                            }
                        }
                    }
                });

                FontIcon icon = new FontIcon(FontAwesome.ARROW_CIRCLE_UP);
                icon.getStyleClass().addAll("button-icon",
                    "add-calendar-button-icon");


                dialog.show();
            }
        );


        this.calendarView.getCalendarSources().setAll(
            prepareCalendarSource(
                resources.getString("sections"),
                //TODO - Section Entity to Section
                section.getEntity()
            )
        );

        this.calendarView.addEventHandler(LoadEvent.LOAD, event -> {
            LOG.debug("Calendar Load Event, loading");
            LOG.debug("TODO: Would add lazy loading strategy here");
        });

        // Hide calendarView by clicking on Duty and load new Window for DutyScheduleView.
        this.calendarView.setEntryDetailsCallback(
            new Callback<DateControl.EntryDetailsParameter, Boolean>() {
                @Override
                public Boolean call(DateControl.EntryDetailsParameter param) {
                    if (param.getEntry() instanceof Entry) {
                        Entry<Duty> entry = (Entry<Duty>) param.getEntry();
                        MasterController mc = MasterController.getInstance();
                        if (mc.get("CalendarController") instanceof CalendarController) {
                            CalendarController cc =
                                (CalendarController) mc.get("CalendarController");
                            cc.hide();
                        }
                        if (dutyScheduleController == null) {
                            if (mc
                                .get("DutyScheduleController") instanceof DutyScheduleController) {
                                dutyScheduleController =
                                    (DutyScheduleController) mc.get("DutyScheduleController");
                                dutyScheduleController.setDuty(entry.getUserObject());
                                dutyScheduleController.setSection(section);
                                dutyScheduleController.show();
                            }
                        } else {
                            dutyScheduleController.setDuty(entry.getUserObject());
                            dutyScheduleController.setSection(section);
                            dutyScheduleController.show();
                        }
                        return true;
                    }
                    LOG.error("Unrecognized Calendar Entry: No Duty found");
                    return false;
                }
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
