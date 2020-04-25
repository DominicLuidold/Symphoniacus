package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;

public class OldDutyComboView {
    ActualSectionInstrumentation oldAsi;
    Duty oldDuty;

    String test;

    public OldDutyComboView(Duty oldDuty) {
        this.oldDuty = oldDuty;
    }

    public OldDutyComboView(String test) {
        this.test = test;
    }

    public ActualSectionInstrumentation getOldAsi() {
        return oldAsi;
    }

    public void setOldAsi(ActualSectionInstrumentation oldAsi) {
        this.oldAsi = oldAsi;
    }

    @Override
    public String toString() {
        return test;
        //return oldDuty.getTitle() + " " + oldDuty.getEntity().getStart();
    }
}
