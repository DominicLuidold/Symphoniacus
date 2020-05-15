package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;

public interface IVacationEntity {
    Integer getVacationId();

    void setVacationId(Integer vacationId);

    MusicianEntity getMusician();

    void setMusician(MusicianEntity musician);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    boolean getIsConfirmed();

    void setIsConfirmed(boolean isConfirmed);
}
