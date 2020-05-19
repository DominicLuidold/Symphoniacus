package at.fhv.orchestraria;

import at.fhv.orchestraria.application.PasswordException;
import at.fhv.orchestraria.application.PasswordManager;
import at.fhv.orchestraria.persistence.dao.DaoBase;
import at.fhv.orchestraria.persistence.dao.JPADatabaseFacade;
import at.fhv.orchestraria.domain.model.UserEntity;

public class Main{
    public static void main(String[] args) throws PasswordException {
//        launch();
        DaoBase<UserEntity> uDAO = JPADatabaseFacade.getInstance().getDAO(UserEntity.class);
        for(UserEntity user : uDAO.getAll()){
            PasswordManager.setNewPassword(user, "password");
            uDAO.save(user);
        }
//        if(uDAO.get(100000019).isPresent()){
//            UserEntity user = uDAO.get(100000019).get();
//            PasswordManager.setNewPassword(user,"password");
//            uDAO.save(user);
//        }

//        DutyPositionDAO dpDAO = JPADatabaseFacade.getDutyPositionDAO();
//        for(DutyPositionEntity dpe : dpDAO.getAll()){
//            dpe.setMusicianByMusicianId(null);
//            dpDAO.update(dpe);
//        }
    }

//Remove all
//    @Override
//    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, Test JavaFX" + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//    }
}