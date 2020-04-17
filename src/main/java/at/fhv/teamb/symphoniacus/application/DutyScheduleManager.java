package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import java.util.List;

public class DutyScheduleManager {

    public ActualSectionInstrumentation getInstrumentationDetails(Duty duty, Section section) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Musician> getMusiciansAvailableForPosition(DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setMusicianForPosition(Musician musician, DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
