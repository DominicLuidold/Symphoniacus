package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "administrativeAssistant")
public class AdministrativeAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private AdministrativeAssistantType description;

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AdministrativeAssistantType getDescription() {
        return this.description;
    }

    public void setDescription(AdministrativeAssistantType description) {
        this.description = description;
    }
}
