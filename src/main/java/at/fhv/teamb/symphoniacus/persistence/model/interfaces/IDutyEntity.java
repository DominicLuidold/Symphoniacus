package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WeeklyScheduleEntity;
import java.time.LocalDateTime;
import java.util.Set;

public interface IDutyEntity {

    void setDutyCategory(DutyCategoryEntity dutyCategory);//TODO: besprechen.
    void setIDutyCategory(IDutyCategoryEntity iDutyCategory);
}
