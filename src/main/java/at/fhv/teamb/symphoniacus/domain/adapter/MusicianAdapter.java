package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.Imodel.IMusician;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDutyPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableMusician;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableSection;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableUser;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MusicianAdapter implements IntegratableMusician {
    private final IMusicianEntity musician;

    public MusicianAdapter(IMusicianEntity musician) {
        this.musician = musician;
    }

    @Override
    public Collection<IntegratableDutyPosition> getIntegratableDutyPositions() {
        List<IntegratableDutyPosition> dutyPositions = new LinkedList<>();
        for (IDutyPositionEntity dutyPosition : this.musician.getDutyPositions()) {
            dutyPositions.add(new DutyPositionAdapter(dutyPosition));
        }
        return dutyPositions;
    }

    @Override
    public IntegratableSection getSection() {
        return new SectionAdapter(this.musician.getSection());
    }

    @Override
    public IntegratableUser getUser() {
        return new UserAdapter(this.musician);
    }
}
