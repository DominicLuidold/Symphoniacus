package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "administrativeAssistant")
public class AdministrativeAssistantEntity implements IAdministrativeAssistantEntity {
    @Id
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private AdministrativeAssistantType description;

    @Override
    public UserEntity getUser() {
        return this.user;
    }

    @Override
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public AdministrativeAssistantType getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(AdministrativeAssistantType description) {
        this.description = description;
    }
}