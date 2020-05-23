package at.fhv.teamb.symphoniacus.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Musician} domain class.
 *
 * @author Valentin Goronjic
 */
public class MusicianTest {
    private IMusicianEntity entity;
    private Musician musician;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
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
        assertEquals(
            "Testi Testnachname",
            this.musician.getFullName(),
            "Full Name should be valid"
        );
    }

    @Test
    public void testGetBalancePoints_shouldThrowException() {
        // Given: Musician where balance points are not calculated
        // When: We call getBalancePoints()
        // Then: An Exception should be thrown
        assertThrows(
            PointsNotCalculatedException.class,
            () -> this.musician.getBalancePoints(),
            "Should throw Exception when points haven't been calculated"
        );
    }

    @Test
    public void testGetBalancePoints_shouldNotThrowException() {
        // Given: Musician where balance points have been calculated
        this.musician.setBalancePoints(Points.getZeroPoints());

        // When: We call getBalancePoints()
        // Then: No exception should be thrown
        assertDoesNotThrow(
            () -> this.musician.getBalancePoints(),
            "Should not throw Exception when points have been calculated"
        );
    }
}
