package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    private static JFXSpinner SPINNER;
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
     * @deprecated in favor of {@link Parentable} interface methods
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

    /**
     * Adds and shows a Loading Spinner in the given AnchorPane.
     *
     * @param pane The AnchorPane which will have the Spinner added at the bottom-center
     */
    public static void enableSpinner(AnchorPane pane) {
        Scene s = pane.getScene();
        SPINNER = new JFXSpinner();
        SPINNER.setPrefSize(50, 50);

        pane.setPrefSize(s.getWidth(), s.getHeight());
        pane.setMinSize(s.getWidth(), s.getHeight());
        pane.getChildren().add(SPINNER);
        AnchorPane.setBottomAnchor(SPINNER, Double.valueOf(50));
        AnchorPane.setLeftAnchor(SPINNER, (pane.getWidth() - SPINNER.getPrefWidth()) / 2);
    }

    /**
     * Removes an active Loading Spinner in the given AnchorPane.
     *
     * @param pane The AnchorPane which will have the Spinner removed again
     */
    public static void disableSpinner(AnchorPane pane) {
        pane.getChildren().remove(SPINNER);
        SPINNER = null;
    }

    /**
     * Gets an {@link Initializable}.
     *
     * @param key The key to identify the Initializable
     * @deprecated in favor of {@link Parentable} interface methods
     */
    public Initializable get(Object key) {
        return map.get(key);
    }

    /**
     * Stores a Initializable with a given String as key.
     *
     * @param key   The key to store the Initializable
     * @param value The value to store
     * @deprecated in favor of {@link Parentable} interface methods
     */
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
