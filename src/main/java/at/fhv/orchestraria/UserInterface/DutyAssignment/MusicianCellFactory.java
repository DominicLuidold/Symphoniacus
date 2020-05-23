package at.fhv.orchestraria.UserInterface.DutyAssignment;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class MusicianCellFactory implements Callback<ListView<UIMusician>, ListCell<UIMusician>> {
    @Override
    public ListCell<UIMusician> call(ListView<UIMusician> listview) {
        return new MusicianListViewCell();
    }
}
