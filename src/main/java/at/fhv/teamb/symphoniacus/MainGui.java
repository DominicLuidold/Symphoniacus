package at.fhv.teamb.symphoniacus;

import java.io.IOException;
import java.util.Locale;
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
            stage.show();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    public static void main() {
        /*
        UserDao userDao = new UserDao();
        LoginManager loginManager = new LoginManager();
        List<UserEntity> list = userDao.findAll();
        LOG.debug("List size? " + list);
        for (UserEntity u : list) {
            System.out.println(u.getUserId() + " | Shortcut: " +u.getShortcut());
            String oldPass = u.getPassword();
            try {
                u.setPassword(u.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Old? " + oldPass + " | new: " + u.getPassword() + "| salt: "+ u.getPasswordSalt());
            System.out.println("-------");

            try {
                Optional<String> genHash = u.getHashFromPlaintext(oldPass);
                boolean eq = genHash.get().equals(u.getPassword());

                if (eq) {
                    System.out.println("great");
                    userDao.update(u);
                } else {
                    System.err.println("Not so great");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } */
        launch();
    }
}
