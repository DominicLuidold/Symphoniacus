package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import java.util.List;

public interface IMusicianRoleDao {
    List<IMusicianRole> getAll();
}
