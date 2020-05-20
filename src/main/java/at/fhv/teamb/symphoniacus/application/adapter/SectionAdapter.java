package at.fhv.teamb.symphoniacus.application.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDutyPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableSection;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SectionAdapter implements IntegratableSection {
    private ISectionEntity section;

    public SectionAdapter(ISectionEntity section) {
        this.section = section;
    }

    @Override
    public Collection<IntegratableDutyPosition> getIntegratableDutyPositions() {
        List<IntegratableDutyPosition> dutyPositions = new LinkedList<>();
        for (IDutyPositionEntity dutyPosition : this.section.getDutyPositions()) {
            dutyPositions.add(new DutyPositionAdapter(dutyPosition));
        }
        return dutyPositions;
    }
}
