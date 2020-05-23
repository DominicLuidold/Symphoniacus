package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests for the {@link Musician} domain class.
 *
 * @author Valentin Goronjic
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MusicianTest {
    private IMusicianEntity entity;
    private Musician musician;

    /**
     * Initial setup for each test.
     */
    @BeforeAll
    public void setUp() {
        IMusicianEntity entity = new MusicianEntity();
        IUserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Testi");
        userEntity.setLastName("Testnachname");
        entity.setUser(userEntity);
        
        this.musician = new Musician(entity);
        this.entity = entity;
    }

    @Test
    public void testGetFullName_shouldReturnFullName() {
        Assertions.assertEquals(
            "Testi Testnachname",
            this.musician.getFullName(),
            "Full Name should be valid"
        );
    }
}
