package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "administrativeAssistant")
public class AdministrativeAssistant {
    @Id
    @Column(name = "userId")
    private Integer _userId;

    @Column(name = "description")
    private String _description;


    public Integer getUserId() {
        return _userId;
    }

    public void setUserId(Integer userId) {
        _userId = userId;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }
}
