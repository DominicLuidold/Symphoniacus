package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LoginManager.
 *
 * @author : Danijel Antonijevic
 * @created : 10.04.20, Fr.
 **/
public class LogInManagerTest {

    private LogInManager logInManager = new LogInManager();

    @Test
    public void login_shouldCreateDomainUser() {
        assertNull(this.logInManager.currentLoggedInUser);
        logInManager.login("TestUser", "TestPassword");
        logInManager.currentLoggedInUser = Mockito.mock(User.class);
        assertNotNull(this.logInManager.currentLoggedInUser);
        assertEquals(this.logInManager.currentLoggedInUser.getShortcut(), "TestUser");
    }


    @Test
    public void getCurrentMusicianRole_shouldReturnCurrentMusicianRole() {
        Musician m = Mockito.mock(Musician.class);
        MusicianRole role = new MusicianRole();
        role.setMusicianRoleId(2);
        m.addMusicianRoles(role);
        this.logInManager.currentLoggedInUser = m;
        assertNotNull(this.logInManager.currentLoggedInUser);
        assertTrue(this.logInManager.currentLoggedInUser instanceof DomainMusician);
        DomainMusician musician = (DomainMusician) this.logInManager.currentLoggedInUser;
        assertNotNull(musician.getMusicianRoles());
        assertTrue(musician.isDutyScheduler());
    }

    public void getSectionsAccessibleToUser() {
        DomainMusician m = Mockito.mock(DomainMusician.class);
        Section section = new Section();
        section.setSectionId(1);
        m.setSection(section);
        assertNotNull(this.logInManager.currentLoggedInUser);

    }
}
