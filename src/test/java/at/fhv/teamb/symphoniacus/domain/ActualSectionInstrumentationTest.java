package at.fhv.teamb.symphoniacus.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests for the {@link Musician} domain class.
 *
 * @author Valentin Goronjic
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActualSectionInstrumentationTest {

    private ActualSectionInstrumentation asi;
    private Musician musician;
    private DutyPosition dutyPosition;

    /**
     * Initial setup for each test.
     */
    @BeforeAll
    public void setUp() {
        IDutyEntity entity = new DutyEntity();
        Duty duty = new Duty(entity);
        duty.setPersistenceState(PersistenceState.PERSISTED);
        this.asi = new ActualSectionInstrumentation(duty);

        IMusicianEntity musicianEntity = new MusicianEntity();
        IUserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Testi");
        userEntity.setShortcut("TestUser");
        musicianEntity.setUser(userEntity);
        this.musician = new Musician(musicianEntity);
        IDutyPositionEntity dutyPositionEntity = new DutyPositionEntity();
        this.dutyPosition = new DutyPosition(dutyPositionEntity, mock(MusicalPiece.class));
    }

    @Test
    public void getPersistenceState_shouldReturnPersistenceStateOfDuty() {
        assertEquals(this.asi.getPersistenceState(), PersistenceState.PERSISTED);
    }

    @Test
    public void assignMusicianToPosition_shouldAssignAMusicianToAPosition() {
        // Given: Musician / DutyPosition from setUp method

        // When: we assign the musician to the position
        this.asi.assignMusicianToPosition(musician, this.dutyPosition);

        // Then: Musician should be assigned to this Position
        assertTrue(
            this.dutyPosition.getAssignedMusician().isPresent(),
            "Assigned Musician is Present"
        );
        assertTrue(
            musician.getAssignedDutyPositions().contains(this.dutyPosition.getEntity()),
            "Musician DutyPositions should contain new DutyPositions"
        );
        assertEquals(this.dutyPosition.getAssignedMusician().get().getShortcut(),
            this.musician.getShortcut(), "DutyPosition should have new Musician assigned");
    }

    @Test
    public void removeMusicianToPosition_shouldAssignAMusicianToAPosition() {
        // Given: Musician / DutyPosition from setUp method

        // When: we remove the musician from the position
        this.asi.removeMusicianFromPosition(this.musician, this.dutyPosition);

        assertTrue(
            this.dutyPosition.getAssignedMusician().isEmpty(),
            "Assigned Musician has been cleared"
        );
        assertFalse(this.musician.getAssignedDutyPositions().contains(
            this.dutyPosition.getEntity()
        ), "Assigned Duty Positions does not contain dp anmore");
    }
}
