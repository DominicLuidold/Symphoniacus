package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    Set<IInstrumentationEntity> getInstrumentations();

    void addInstrumentation(IInstrumentationEntity entity);

    void removeInstrumentation(IInstrumentationEntity entity);
}
