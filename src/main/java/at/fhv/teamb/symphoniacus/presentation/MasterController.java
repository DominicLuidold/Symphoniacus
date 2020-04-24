package at.fhv.teamb.symphoniacus.presentation;

import java.util.Map;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.commons.collections4.map.HashedMap;
import org.controlsfx.control.StatusBar;

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

    public Initializable get(Object key) {
        return map.get(key);
    }

    public Initializable put(String key, Initializable value) {
        return map.put(key, value);
    }

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
