package at.fhv.teamb.symphoniacus;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"), bundle);

            Scene scene = new Scene(root);
            stage.setTitle("Symphoniacus");
            stage.setScene(scene);
            stage.setMinHeight(768);
            stage.setMinWidth(1366);
            stage.show();

            DutyEntity entity = new DutyEntity();
            entity.setDutyId(4);
            MusicianEntity musicianEntity = new MusicianEntity();
            musicianEntity.setMusicianId(9);
            DutyDao dao = new DutyDao();
            List<DutyEntity> result = dao.getAllDutiesInRangeFromMusician(musicianEntity, LocalDate.of(2020,5,3));
            System.out.println();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
