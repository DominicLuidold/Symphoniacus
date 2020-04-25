package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;

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
}
