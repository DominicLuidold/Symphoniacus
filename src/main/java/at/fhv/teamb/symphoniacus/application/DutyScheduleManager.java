package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPosition;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.util.LinkedList;
import java.util.List;

public class DutyScheduleManager {

    public ActualSectionInstrumentation getInstrumentationDetails(Duty duty, Section section) {
        return null;
    }

    public List<Musician> getMusiciansAvailableForPosition(
        DutyPosition dp,
        boolean withRequests
    ) {
        return new LinkedList<>();
    }


}
