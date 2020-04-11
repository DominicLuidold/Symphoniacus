package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.roleEnum.AdministrativeAssistantEnum;

import javax.persistence.*;

@Entity
@Table(name = "administrativeAssistant")
public class AdministrativeAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private AdministrativeAssistantEnum description;

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AdministrativeAssistantEnum getDescription() {
        return this.description;
    }

    public void setDescription(AdministrativeAssistantEnum description) {
        this.description = description;
    }
}
