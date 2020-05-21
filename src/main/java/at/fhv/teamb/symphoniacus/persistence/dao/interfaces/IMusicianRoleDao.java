package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import java.util.List;

public interface IMusicianRoleDao {
    List<IMusicianRole> getAll();
}
