package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;
import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Valentin
 */
public class MainControllerTest {

    private MainController mainController = new MainController();

    @Test
    public void testGetPermittedTabs_shouldReturnAListOfPermittedTabsForMusician() {
        MusicianEntity entity = new MusicianEntity();
        UserEntity user = new UserEntity();
        user.setFirstName("Max");
        entity.setUser(user);
        Musician m = new Musician(entity);
        List<String> tabs = mainController.getPermittedTabs(
            DomainUserType.DOMAIN_MUSICIAN,
            m
        );
        Assertions.assertTrue(
            tabs.isEmpty(),
            "Should see no view atm"
        );
    }

    @Test
    public void testGetPermittedTabs_shouldReturnAListOfPermittedTabsForMusicianWithRoles() {
        // Given: Musician is Musician with Duty Scheduler role
        MusicianEntity entity = new MusicianEntity();
        UserEntity user = new UserEntity();
        user.setFirstName("Max");
        entity.setUser(user);
        MusicianRole role = new MusicianRole();
        role.setMusicianRoleId(2);
        role.setDescription(MusicianRoleType.DUTY_SCHEDULER);
        Musician m = new Musician(entity);

        // When: we call getPermittedTabs from TabPaneController
        List<String> tabs = mainController.getPermittedTabs(
            DomainUserType.DOMAIN_MUSICIAN,
            m
        );

        // Then: We should see dutySchedulerCalendar and dutySchedule (last one hidden) - Tabs
        Assertions.assertTrue(
            tabs.contains("dutySchedulerCalendar.fxml"),
            "Should contain dutySchedulerCalendar"
        );

        Assertions.assertTrue(
            tabs.contains("dutySchedule.fxml"),
            "Should contain duty Schedule view"
        );
    }

    @Test
    public void testGetPermittedTabs_shouldReturnAListOfPermittedTabsForAssistant() {
        AdministrativeAssistantEntity entity = new AdministrativeAssistantEntity();
        AdministrativeAssistant aa = new AdministrativeAssistant(entity);
        List<String> tabs = mainController.getPermittedTabs(
            DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT,
            aa
        );
        Assertions.assertTrue(
            tabs.contains("organizationalOfficerCalendarView.fxml"),
            "Should contain organizational officer view"
        );
    }

}
