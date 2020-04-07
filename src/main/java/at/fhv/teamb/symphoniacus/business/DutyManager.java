package at.fhv.teamb.symphoniacus.business;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDAO;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DutyManager {
    protected DutyDAO dutyDAO;

    public DutyManager(){
        this.dutyDAO = new DutyDAO();
    }

    public List<Duty> findAllInWeek(LocalDate start) {
        return this.dutyDAO.findAllInWeek(start.atStartOfDay());
    }

    public List<Duty> findAllInWeek(Section sectionOfUser, LocalDate start) {
        return this.dutyDAO.findAllInWeek(sectionOfUser, start.atStartOfDay());
    }

    public List<Duty> findAllInRange(LocalDate start, LocalDate end) {
        return this.dutyDAO.findAllInRange(start.atStartOfDay(), end.atStartOfDay());
    }

    public List<Duty> findAllInRange(Section sectionOfUser, LocalDate start, LocalDate end) {
        return this.dutyDAO.findAllInRange(sectionOfUser, start.atStartOfDay(), end.atStartOfDay());
    }

    public static LocalDate getLastMondayDate(LocalDate givenDate) {
        //Will always jump back to last monday
        return givenDate.with(DayOfWeek.MONDAY);
    }


}
