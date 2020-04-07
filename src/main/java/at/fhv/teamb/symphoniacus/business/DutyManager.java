package at.fhv.teamb.symphoniacus.business;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDAO;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DutyManager {

    public List<Duty> findAllInWeek(LocalDate start) {
        DutyDAO dutyDAO = new DutyDAO();
        return dutyDAO.findAllInWeek(start);
    }

    public List<Duty> findAllInWeek(Section sectionOfUser, LocalDate start) {
        DutyDAO dutyDAO = new DutyDAO();
        return dutyDAO.findAllInWeek(sectionOfUser, start);
    }


    public List<Duty> findAll(LocalDate start, LocalDate end) {
        DutyDAO dutyDAO = new DutyDAO();
        return dutyDAO.findAll(start, end);
    }

    public List<Duty> findAll(Section sectionOfUser, LocalDate start, LocalDate end) {
        DutyDAO dutyDAO = new DutyDAO();
        return dutyDAO.findAll(sectionOfUser, start, end);
    }


    public static LocalDate getLastMondayDate(LocalDate givenDate) {
        //Will always jump back to last monday
        return givenDate.with(DayOfWeek.MONDAY);
    }
}
