package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.DomainMusician;
import at.fhv.teamb.symphoniacus.domain.DomainUser;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.Substitute;
import at.fhv.teamb.symphoniacus.persistence.model.User;

import java.util.List;

/**
 * @author : Danijel Antonijevic
 * @created : 10.04.20, Fr.
 **/
public class UserManager {

    protected UserDao userDao;
    protected DomainUser currentLoggedInUser;

    public UserManager() {
        this.userDao = new UserDao();
    }


     void login(String userShortCut, String userPassword) {
        User currentUser;
        if (userShortCut != null && userPassword != null) {
           currentUser  = this.userDao.login(userShortCut, userPassword);

           if (currentUser != null){
               if (userDao.getUserIsMusician(currentUser) != null) {
                   this.currentLoggedInUser = new DomainMusician();
                   this.currentLoggedInUser.setUser(currentUser);

               }
           }
        }



        if (currentUser != null) {

        }   if (userDao.getUserIsMusician(currentUser) != null) {
            this.currentLoggedInUser = new DomainMusician();
            if (userDao.getUserIsOrchestraLibrarian(currentUser) != null) {
                this.currentLoggedInUser
                    .addMusicianRoles(userDao.getUserIsOrchestraLibrarian(currentUser));
            } else if (userDao.getUserIsDutyScheduler(currentUser) != null) {
                this.currentLoggedInUser
                    .addMusicianRoles(userDao.getUserIsOrchestraLibrarian(currentUser));
            } else if (userDao.getUserIsSubstitute(currentUser) != null) {
                //TODO create DomainSubstitute
            }
        } else {

            if (userDao.getUserIsOrganizationalOfficer(currentUser) != null) {

            } else if (userDao.getUserIsMusicLibrarian(currentUser) != null) {

            } else if (userDao.getUserIsSectionPrincipal(currentUser) != null) {

            }
        }

    }

    public List<MusicianRole> getCurrentMusicianRole() {

       /* if(musician != null){
            List<MusicianRole> roles = userDao.getRolesFromUser(musician);
            for(MusicianRole role: roles){
                this.currentLoggedInUser.
            }

        }*/

        return null;
    }

    public List<AdministrativeAssistant> getAdministrativeAssistantRoles() {

    }

    public List<Section> getSectionsAccessibleToUser(User user) {
        return null;
    }





/*    getCurrentRole()
    getSectionsAccessibleToUser()*/


}

