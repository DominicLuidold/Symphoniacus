package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Duty;

/**
 * This class is for Displaying the old Dutys in the Dutyscheduleview Dropdown.
 *
 * @author Tobias Moser
 */

public class OldDutyComboView {
    Duty oldDuty;

    public OldDutyComboView(Duty oldDuty) {
        this.oldDuty = oldDuty;
    }

    public String getTitle() {
        return oldDuty.getTitle();
    }

    public String getStart() {
        return oldDuty.getEntity().getStart().toString();
    }

    public String getType() {
        return oldDuty.getEntity().getDutyCategory().getType();
    }

    public Duty getOldDuty() {
        return oldDuty;
    }
}
