package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;

public interface IContractualObligationEntity {
    Integer getContractNr();

    void setContractNr(Integer contractNr);

    String getPosition();

    void setPosition(String position);

    Integer getPointsPerMonth();

    void setPointsPerMonth(Integer pointsPerMonth);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    MusicianEntity getMusician();

    void setMusician(MusicianEntity musician);

    IInstrumentCategoryEntity getInstrumentCategory();

    void setInstrumentCategory(
        IInstrumentCategoryEntity instrumentCategory
    );
}
