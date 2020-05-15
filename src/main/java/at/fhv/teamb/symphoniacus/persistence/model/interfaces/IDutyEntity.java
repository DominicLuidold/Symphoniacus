package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDateTime;

/**
 * Interface for DutyEntity class.
 *
 * @author Theresa Gierer
 */
public interface IDutyEntity {
    LocalDateTime getStart();

    LocalDateTime getEnd();

    IDutyCategoryEntity getDutyCategory();

    ISeriesOfPerformancesEntity getSeriesOfPerformances();

    String getDescription();

    Integer getDutyId();

}
