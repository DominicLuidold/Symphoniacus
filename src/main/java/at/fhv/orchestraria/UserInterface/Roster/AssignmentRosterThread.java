package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.UserInterface.DutyAssignment.DutyAssignmentWindowController;
import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import at.fhv.orchestraria.application.DutyAssignmentController;
import at.fhv.orchestraria.domain.Imodel.IDuty;
import at.fhv.orchestraria.domain.Imodel.IMusician;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDuty;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import java.util.Collection;
import java.util.HashMap;

public class AssignmentRosterThread extends RosterThread{

    private IMusician _musicianEntity;
    private DutyAssignmentController _dAssController;
    private RosterWindowController _rosterWindowController;
    private HashMap<IDuty, OrchestraEntry> _assignedDutyMapping = new HashMap<>();
    private HashMap<IDuty, OrchestraEntry> _unassignedDutyMapping = new HashMap<>();
    private Calendar _unassignedDuties = new Calendar("Unassigned");
    private Calendar _assignedDuties = new Calendar("Assigned");
    private IDuty _duty;
    private CalendarView _cview;

    public static boolean isPreload = false;

    //For testing porpuses
    private final int VL1_SECTION = 1;
    private final int VL2_SECTION = 30;
    private final int VLA_SECTION = 50;
    private final int VC_SECTION = 67;
    private final int KB_SECTION = 80;
    private final int WOODWIND_SECTION = 100;
    private final int BRASS_SECTION = 120;
    private final int PERCUSSION_SECTION = 145;




    public AssignmentRosterThread(RosterWindowController rosterWindowController, CalendarView cview){
        _rosterWindowController = rosterWindowController;
        _cview = cview;
    }


    /**
     * Loads the DutyAssignmentController and enters all entries of the section into the CalendarView. Also sets the PopOverType of the CalendarView.
     */
    @Override
    public void run() {
        _musicianEntity = LoginWindowController.getLoggedInUser().getMusician();
        _dAssController = new DutyAssignmentController(_musicianEntity.getSection().getSectionId());

        //Set Popover to custom Popover
        _cview.setEntryDetailsPopOverContentCallback(param -> new PopOverDutyScheduler(param.getPopOver(), param.getDateControl(), param.getNode(), param.getEntry(),_dAssController, this));

        enterEntries();

        DutyAssignmentWindowController dutyAssignmentWindowController = new DutyAssignmentWindowController();
        dutyAssignmentWindowController.initThread(_dAssController, _duty);
        isPreload = true;
    }


    /**
     * Get all Entries of the user and put them into the calendar.
     */
    private synchronized void enterEntries() {
        _unassignedDuties = getUnassignedDuties();
        _assignedDuties = getAssignedDuties();

        _unassignedDuties.setReadOnly(true);
        _assignedDuties.setReadOnly(true);

        _unassignedDuties.setStyle(Calendar.Style.STYLE5);
        _assignedDuties.setStyle(Calendar.Style.STYLE1);

        //Add all Duties to the Calendars
        Collection<IDuty> duties = _dAssController.getDuties();
        for(IDuty d : duties){
            _duty = d;
            OrchestraEntry<IntegratableDuty> e  = new OrchestraEntry<IntegratableDuty>((IntegratableDuty)d);
            sortDuty(e,d);
        }
    }

    /**
     * Creates a CalendarSource and connects it to the CalendarView.
     * @return The created CalendarSource
     */
    @Override
    public CalendarSource getSources(){
        CalendarSource myCalendarSource = new CalendarSource("Calendraria");

        //Add the duty rosters
        myCalendarSource.getCalendars().addAll(_unassignedDuties, _assignedDuties);

        return myCalendarSource;
    }

    /**
     * Sort the duty into one of the two calendars.
     * @param entry The OrchestraEntry corresponding to the duty.
     * @param duty The duty that needs to be sorted.
     */
    private synchronized void sortDuty(OrchestraEntry<IntegratableDuty> entry, IDuty duty) {
        if(duty.isSectionCompletelyAssigned(_dAssController.getSectionID())){
            _assignedDutyMapping.put(duty,entry);
            _assignedDuties.addEntry(entry);
        }else{
            _unassignedDutyMapping.put(duty,entry);
            _unassignedDuties.addEntry(entry);
        }
    }

    public HashMap<IDuty, OrchestraEntry> getAssignedDutyMapping() {
        return _assignedDutyMapping;
    }

    public HashMap<IDuty, OrchestraEntry> getUnassignedDutyMapping() {
        return _unassignedDutyMapping;
    }

    public Calendar getUnassignedDuties(){
        return _unassignedDuties;
    }

    public Calendar getAssignedDuties() {
        return _assignedDuties;
    }


    /**
     * Looks up if the assignment status of the duty (completely assigned/ not completely assigned) and sorts it in the corresponding calendar.
     * @param duty The duty of which the assignment status should be reviewed.
     */
    public synchronized void setNewEntryStatus(IDuty duty) {
        _assignedDutyMapping = getAssignedDutyMapping();
        _unassignedDutyMapping = getUnassignedDutyMapping();
        if(_assignedDutyMapping.containsKey(duty)){
            _assignedDuties.removeEntry(_assignedDutyMapping.get(duty));
            sortDuty(_assignedDutyMapping.get(duty), duty);
        }else if (_unassignedDutyMapping.containsKey(duty)){
            _unassignedDuties.removeEntry(_unassignedDutyMapping.get(duty));
            sortDuty(_unassignedDutyMapping.get(duty), duty);
        }
    }
}
