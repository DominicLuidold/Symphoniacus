package at.fhv.teamb.symphoniacus.business;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DutyManager {
    protected DutyDao dutyDao;

    public DutyManager() {
        this.dutyDao = new DutyDao();
    }

    public List<Duty> findAllInWeek(LocalDate start) {
        return this.dutyDao.findAllInWeek(start.atStartOfDay());
    }

    public List<Duty> findAllInWeek(Section sectionOfUser, LocalDate start) {
        return this.dutyDao.findAllInRangeWithSection(sectionOfUser, start.atStartOfDay(),
            false, false, false);
    }

    public List<Duty> findAllInRange(LocalDate start, LocalDate end) {
        return this.dutyDao.findAllInRange(start.atStartOfDay(), end.atStartOfDay());
    }

    public List<Duty> findAllInRange(Section sectionOfUser, LocalDate start, LocalDate end) {
        return this.dutyDao.findAllInRangeWithSection(sectionOfUser, start.atStartOfDay(), end.atStartOfDay(),
            false, false, false);
    }

    public static LocalDate getLastMondayDate(LocalDate givenDate) {
        // Will always jump back to last monday
        return givenDate.with(DayOfWeek.MONDAY);
    }
}
