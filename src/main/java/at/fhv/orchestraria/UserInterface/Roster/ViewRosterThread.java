package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDuty;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDutyPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableMusician;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import java.util.HashSet;

public class ViewRosterThread extends RosterThread {

    private CalendarView _cview;
    private IntegratableMusician _musicianEntity;


    //For testing porpuses
    private final int VL1_SECTION = 1;
    private final int VL2_SECTION = 30;
    private final int VLA_SECTION = 50;
    private final int VC_SECTION = 67;
    private final int KB_SECTION = 80;
    private final int WOODWIND_SECTION = 100;
    private final int BRASS_SECTION = 120;
    private final int PERCUSSION_SECTION = 145;
    private Calendar _tourDuties = new Calendar("Tours");
    private Calendar _rehearsalDuties = new Calendar("Rehearsals");
    private Calendar _performanceDuties = new Calendar("Performances");


    public ViewRosterThread(CalendarView cview, IntegratableMusician musician) {
        super();
        _cview = cview;
        _musicianEntity = musician;
    }


    /**
     * Enters all entries of the section into the CalendarView. Also sets the PopOverType of the CalendarView.
     */
    @Override
    public void run() {

        /* Not used in integration
            _musicianEntity = (IntegratableMusician) LoginWindowController.getLoggedInUser().getMusician();
         */

        enterEntries();

        //Set Popover to custom Popover
        _cview.setEntryDetailsPopOverContentCallback(
            param -> new PopOverMusician(
                param.getPopOver(),
                param.getDateControl(),
                param.getNode(),
                param.getEntry(),
                _musicianEntity
            )
        );
    }

    /**
     * Get all Entries of the user and put them into the calendar.
     */
    private synchronized void enterEntries() {
        _tourDuties.setStyle(Calendar.Style.STYLE4);
        _tourDuties.setReadOnly(true);

        _rehearsalDuties.setStyle(Calendar.Style.STYLE6);
        _rehearsalDuties.setReadOnly(true);

        _performanceDuties.setStyle(Calendar.Style.STYLE5);
        _performanceDuties.setReadOnly(true);


        HashSet<IntegratableDuty> closedList = new HashSet<>();

        for(IntegratableDutyPosition dutyPosition :_musicianEntity.getIntegratableDutyPositions()){

            IntegratableDuty duty = dutyPosition.getDuty();
            if(!closedList.contains(duty)) {
                OrchestraEntry<IntegratableDuty> entry = new OrchestraEntry<IntegratableDuty>(duty);

                if (dutyPosition.getDuty().isRehearsal()) {
                    _rehearsalDuties.addEntry(entry);
                } else if (duty.getSeriesOfPerformances() != null
                        && duty.getSeriesOfPerformances().isTour()) {
                    _tourDuties.addEntry(entry);
                } else {
                    _performanceDuties.addEntry(entry);
                }
                closedList.add(duty);
            }

        }
    }


    /**
     * Creates a CalendarSource and connects it to the CalendarView.
     * @return The created CalendarSource
     */
    @Override
    public CalendarSource getSources() {
        CalendarSource myCalendarSource = new CalendarSource("Calendraria");

        //Add the duty rosters
        myCalendarSource.getCalendars().addAll(_performanceDuties, _rehearsalDuties, _tourDuties);

        return myCalendarSource;
    }
}
