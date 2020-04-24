package at.fhv.teamb.symphoniacus.presentation;

import java.util.Map;
import javafx.fxml.Initializable;
import org.apache.commons.collections4.map.HashedMap;
import org.controlsfx.control.StatusBar;

public class MasterController {

    private static MasterController INSTANCE;

    private MasterController() {
    }

    private Map<String, Initializable>  map = new HashedMap<>();
    private StatusBar statusBar;

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

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }
}
