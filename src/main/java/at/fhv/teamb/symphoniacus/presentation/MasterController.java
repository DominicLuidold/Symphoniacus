package at.fhv.teamb.symphoniacus.presentation;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.collections4.map.HashedMap;
import org.controlsfx.control.StatusBar;

/**
 * Little helper to assist us in various UI tasks.
 * Mainly needed to switch visibiltiy in {@link DutyScheduleController}.
 *
 * @author Valentin Goronjic
 */
public class MasterController {

    private static MasterController INSTANCE;
    private Label statusTextField;
    private Map<String, Initializable> map = new HashedMap<>();
    private StatusBar statusBar;

    private MasterController() {
        this.statusTextField = new Label();
    }

    /**
     * Returns the instance of MasterController (Singleton).
     *
     * @return The one and only wonderful instanceof {@link MasterController}
     */
    public static MasterController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MasterController();
        }

        return INSTANCE;
    }

    /**
     * TODO JAVADOC.
     */
    public static <T> T switchSceneTo(String fxmlPath, ResourceBundle bundle, Node node)
        throws IOException {
        Scene oldScene = node.getScene();
        Stage stage = (Stage) oldScene.getWindow();

        FXMLLoader loader = new FXMLLoader(MasterController.class.getResource(fxmlPath), bundle);
        Parent parent = loader.load();
        T controller = loader.getController();
        Scene newScene = new Scene(parent, oldScene.getWidth(), oldScene.getHeight());
        newScene.getStylesheets().add("css/styles.css");
        stage.setScene(newScene);

        return controller;
    }

    public Initializable get(Object key) {
        return map.get(key);
    }

    public Initializable put(String key, Initializable value) {
        return map.put(key, value);
    }

    /**
     * Sets the StatusBar. Is called by {@link TabPaneController} once.
     *
     * @param statusBar The StatusBar which is defined in {@link TabPaneController} FXML
     */
    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
        this.statusTextField.textProperty().addListener(
            it -> {
                System.out.println("set to " + this.statusTextField.getText());
                this.statusBar.setText(this.statusTextField.getText());
            }
        );
        this.statusTextField.setText("Loaded");
    }

    public void showStatusBarLoading() {
        this.statusTextField.setText("Loading");
    }

    public void showStatusBarLoaded() {
        this.statusTextField.setText("Loaded");
    }
}
