package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableUser;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;

public class UserAdapter implements IntegratableUser {
    private final IUserEntity user;

    public UserAdapter(IMusicianEntity musician) {
        this.user = musician.getUser();
    }

    @Override
    public Object getFirstName() {
        return this.user.getFirstName();
    }

    @Override
    public String getShortcut() {
        return this.user.getShortcut();
    }
}
