package at.fhv.teamb.symphoniacus.presentation.internal;

import java.util.Objects;

/**
 * A Tab Pane entry which is mainly needed for
 * {@link at.fhv.teamb.symphoniacus.presentation.MainController}
 * to build the Tab Pane Header menu.
 *
 * @author Valentin
 */
public enum TabPaneEntry {
    ORG_OFFICER_CALENDAR_VIEW(1,"Duty Roster","/view/organizationalOfficerCalendarView.fxml"),
    UNSUPPORTED(1,"Unsupported","/view/unsupportedTab.fxml"),
    DUTY_SCHEDULER_CALENDAR_VIEW(1,"Duty Roster","/view/dutySchedulerCalendar.fxml"),
    DUTY_SCHEDULER_SCHEDULE_VIEW(2,"Duty Schedule", "/view/dutySchedule.fxml");

    private int order;
    private String title;
    private String fxmlPath;

    /**
     * Constructs a new TabPaneEntry.
     *
     * @param order    The order in which this FXML should be loaded (1 = first)
     * @param title    Title of Tab
     * @param fxmlPath Path to FXML file including /view/ as prefix
     */
    TabPaneEntry(int order, String title, String fxmlPath) {
        this.order = order;
        this.title = title;
        this.fxmlPath = fxmlPath;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

}
