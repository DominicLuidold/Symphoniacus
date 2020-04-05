
import javax.persistence.*;

@Entity
@Table(name = "administrativeAssistant")
public class AdministrativeAssistant {
    @Id
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "description")
    private String description;


    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
