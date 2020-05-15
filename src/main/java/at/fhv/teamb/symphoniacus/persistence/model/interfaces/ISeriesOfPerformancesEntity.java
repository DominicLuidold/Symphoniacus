package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;

/**
 * Interface for SeriesOfPerformancesEntity class.
 *
 * @author Theresa Gierer
 */
public interface ISeriesOfPerformancesEntity {
    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getSeriesOfPerformancesId();

}
