package at.fhv.teamb.symphoniacus.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPiece;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MusicianManagerTest {
    private MusicianManager musicianManager;

    @BeforeAll
    public void initalize() {
        this.musicianManager = new MusicianManager();
    }

    @Test
    void findLoadMusician_shouldReturnAMusician() {
        User u = new User();
        u.setUserId(1);

        Optional<Musician> m = this.musicianManager.loadMusician(u);
        assertTrue(m.isPresent());
    }
}
