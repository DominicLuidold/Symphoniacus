package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for DutyDao class.
 *
 * @author Theresa Gierer
 */
public interface IDutyDao extends Dao<IDutyEntity> {

    /**
     * Checks whether a duty with the given parameters exists or not.
     *
     * @param series           given Series of Performances from searched Duty.
     * @param instrumentations given instrumentation from searched Duty.
     * @param startingDate     given starting Date from the searched Duty.
     * @param endingDate       given ending Date from searched Duty.
     * @param category         given dutyCategory from searched Duty.
     * @return True if duty exists, false otherwise
     */
    boolean doesDutyAlreadyExists(
        ISeriesOfPerformancesEntity series,
        List<IInstrumentationEntity> instrumentations,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        IDutyCategoryEntity category);
}
