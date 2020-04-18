package at.fhv.teamb.symphoniacus.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.User;
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
        User u = new User();
        u.setUserId(1);

        // When
        Optional<MusicianEntity> m = this.musicianManager.loadMusician(u);

        // Then
        assertTrue(m.isPresent());
    }
}
