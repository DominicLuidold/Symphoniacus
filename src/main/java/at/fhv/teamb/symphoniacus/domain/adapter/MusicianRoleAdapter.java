package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import at.fhv.orchestraria.domain.Imodel.IMusicianRoleMusician;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRoleEntity;
import java.util.Collection;
import java.util.LinkedList;

public class MusicianRoleAdapter implements IMusicianRole {
    private final IMusicianRoleEntity musicianRole;

    public MusicianRoleAdapter(IMusicianRoleEntity role) {
        musicianRole = role;
    }

    @Override
    public int getMusicianRoleId() {
        return musicianRole.getMusicianRoleId();
    }

    @Override
    public String getDescription() {
        // Solange in Table description nullable ist...
        if (musicianRole.getDescription() != null) {
            return musicianRole.getDescription().toString();
        } else {
            return "-";
        }
    }

    @Override
    public Collection<IMusicianRoleMusician> getIMusicianRoleMusicians() {
        return new LinkedList<>();
    }
}
