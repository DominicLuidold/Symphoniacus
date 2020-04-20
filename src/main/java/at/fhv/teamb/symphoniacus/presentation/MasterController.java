package at.fhv.teamb.symphoniacus.presentation;

import java.util.Map;
import javafx.fxml.Initializable;
import org.apache.commons.collections4.map.HashedMap;

public class MasterController {

    private static MasterController INSTANCE;

    private MasterController() {
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

    private Map<String, Initializable>  map = new HashedMap<>();

    public Initializable get(Object key) {
        return map.get(key);
    }

    public Initializable put(String key, Initializable value) {
        return map.put(key, value);
    }
}
