package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.DomainMusician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Danijel Antonijevic
 * @created : 10.04.20, Fr.
 **/
public class UserManagerTest {

    private UserManager userManager = new UserManager();

    @Test
    public void login_shouldCreateDomainUser(){
        assertNull(this.userManager.currentLoggedInUser);
        userManager.login("TestUser","TestPassword");
        userManager.currentLoggedInUser = Mockito.mock(DomainMusician.class);
        assertNotNull(this.userManager.currentLoggedInUser);
        assertEquals(this.userManager.currentLoggedInUser.getUserShortcut(),"TestUser");
    }

   /* getCurrentRole()
    getSectionsAccessibleToUser()*/

   @Test
    public void getCurrentMusicianRole_shouldReturnCurrentMusicianRole(){
       DomainMusician m = Mockito.mock(DomainMusician.class);
       MusicianRole role = new MusicianRole();
       role.setMusicianRoleId(2);
       m.addMusicianRoles(role);
       this.userManager.currentLoggedInUser = m;
       assertNotNull(this.userManager.currentLoggedInUser);
       assertTrue(this.userManager.currentLoggedInUser instanceof DomainMusician);
       DomainMusician musician = (DomainMusician) this.userManager.currentLoggedInUser;
       assertNotNull(musician.getMusicianRoles());
       assertTrue(musician.isDutyScheduler());
    }

    public void getSectionsAccessibleToUser(){
        DomainMusician m = Mockito.mock(DomainMusician.class);
        Section section = new Section();
        section.setSectionId(1);
        m.setSection(section);
        assertNotNull(this.userManager.currentLoggedInUser);

    }
}
