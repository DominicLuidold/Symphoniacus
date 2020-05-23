package at.fhv.teamb.symphoniacus.application.adapter;

import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import at.fhv.orchestraria.domain.Imodel.IMusicianRoleMusician;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRoleEntity;
import java.util.Collection;

public class MusicianRoleAdapter implements IMusicianRole {
    private final IMusicianRoleEntity musicianRole;

    public MusicianRoleAdapter(
        IMusicianRoleEntity role) {
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

    /**
     * Blub.
     *
     * @return Returns unmodifiable collection of musician roles of
     *      the musicians by musician role ID
     */
    @Override
    public Collection<IMusicianRoleMusician> getIMusicianRoleMusicians() {
        return null;
    }
}
