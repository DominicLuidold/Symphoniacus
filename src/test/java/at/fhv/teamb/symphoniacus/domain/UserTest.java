package at.fhv.teamb.symphoniacus.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link User} domain class.
 *
 * @author Valentin Goronjic
 */
public class UserTest {

    private IUserEntity userEntity;
    private User user;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
    public void setUp() {
        IUserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Testi");
        userEntity.setLastName("Testnachname");
        this.userEntity = userEntity;

        this.user = new User(userEntity);
        this.user.setType(DomainUserType.DOMAIN_MUSICIAN);
    }

    @Test
    public void testGetFullName_shouldReturnFullName() {
        assertEquals(
            "Testi Testnachname",
            this.user.getFullName(),
            "Full Name should be valid"
        );
    }

    @Test
    public void testGetType_shouldReturnType() {
        assertEquals(
            DomainUserType.DOMAIN_MUSICIAN,
            this.user.getType(),
            "Type should be the same as previously set"
        );
    }

    @Test
    public void testGetEntity_shouldReturnEntity() {
        assertEquals(
            this.userEntity,
            this.user.getUserEntity(),
            "Entity should be the same as previously set"
        );
    }
}
