package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.persistence.model.IUserEntity;

public class User {

    private final IUserEntity userEntity;
    protected DomainUserType type;

    public User(IUserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public IUserEntity getUserEntity() {
        return userEntity;
    }

    public DomainUserType getType() {
        return type;
    }

    public void setType(DomainUserType type) {
        this.type = type;
    }

    /**
     * Returns the full name of the user which is "FirstName LastName" (separated by Whitespace).
     * @return String that has the Format "FirstName LastName"
     */
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.userEntity.getFirstName());
        sb.append(" ");
        sb.append(this.userEntity.getLastName());

        return sb.toString();
    }
}
