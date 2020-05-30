package at.fhv.teamb.symphoniacus;

import at.fhv.teamb.symphoniacus.application.WishRequestManager;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishTargetType;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishType;
import at.fhv.teamb.symphoniacus.persistence.dao.WishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IWishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main GUI Controller.
 *
 * @author Valentin Goronjic
 */
public class MainGui extends Application {
    private static final Logger LOG = LogManager.getLogger(MainGui.class);

    @Override
    public void start(Stage stage) {
        try {
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            Parent root = FXMLLoader
                .load(getClass().getResource("/view/login.fxml"), bundle);

            double minHeight = 748;
            double minWidth = 1244;
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - minWidth) / 2);
            stage.setY((screenBounds.getHeight() - minHeight) / 2);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("css/styles.css");
            stage.setTitle("Symphoniacus");
            stage.setScene(scene);
            stage.setMinHeight(minHeight);
            stage.setMinWidth(minWidth);
            stage.getIcons().add(
                new Image(
                    "images/icon.png"
                )
            );
            WishRequestManager manager = new WishRequestManager();
            IWishEntryDao dao = new WishEntryDao();
            Optional<IWishEntryEntity> opWish = dao.find(13);
            dao.remove(opWish.get());
            manager.removeDutyWish(13);
            System.out.println("test");
            stage.show();

        } catch (IOException e) {
            LOG.error(e);
        }
    }




    public static void main() {
        launch();
    }
}
