package at.fhv.teamb.symphoniacus.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MusicianManagerTest {
    private MusicianManager musicianManager;

    @BeforeAll
    void initialize() {
        this.musicianManager = new MusicianManager();
    }

    @Test
    void findLoadMusician_shouldReturnAMusician() {
        // Given
        IUserEntity u = new UserEntity();
        u.setUserId(1);

        // When
        Optional<Musician> m = this.musicianManager.loadMusician(u);

        // Then
        assertTrue(m.isPresent());
    }
}
