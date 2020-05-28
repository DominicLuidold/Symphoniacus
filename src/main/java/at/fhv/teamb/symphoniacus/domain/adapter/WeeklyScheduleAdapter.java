package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableWeeklySchedule;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;

public class WeeklyScheduleAdapter implements IntegratableWeeklySchedule {
    private final IWeeklyScheduleEntity weeklySchedule;

    public WeeklyScheduleAdapter(IWeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    @Override
    public boolean isConfirmed() {
        return this.weeklySchedule.getConfirmed();
    }
}
