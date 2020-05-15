package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;

public interface IVacationEntity {
    Integer getVacationId();

    void setVacationId(Integer vacationId);

    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    boolean getIsConfirmed();

    void setIsConfirmed(boolean isConfirmed);
}
