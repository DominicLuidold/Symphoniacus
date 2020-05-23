package at.fhv.orchestraria.UserInterface.DutyAssignment;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.io.IOException;
public class MusicianListViewCell extends ListCell<UIMusician> {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label shortcutLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private FontAwesomeIcon princIcon;

    @FXML
    private FontAwesomeIcon wishIcon;


    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(UIMusician musician, boolean empty) {
        super.updateItem(musician, empty);

        if (empty || musician == null) {

            setText(null);
            setGraphic(null);

        } else {
            mLLoader = new FXMLLoader(getClass().getResource("/ListCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (musician.isSectionPrincipal()) {
                princIcon.setFill(Color.BLACK);
            } else{
                princIcon.setFill(Color.TRANSPARENT);
            }

            shortcutLabel.setText(musician.getShortcut());
            pointsLabel.setText(musician.getPoints());

            if (musician.getPositiveWish() != null) {
                wishIcon.setIcon(FontAwesomeIcons.THUMBS_UP);
                wishIcon.setFill(Color.GREEN);
            }
            if (musician.getNegativeDateWish() != null) {
                wishIcon.setIcon(FontAwesomeIcons.THUMBS_DOWN);
                wishIcon.setFill(Color.RED);
            }
            if (musician.getNegativeDutyWish() != null) {
                wishIcon.setIcon(FontAwesomeIcons.THUMBS_DOWN);
                wishIcon.setFill(Color.RED);
            }

            setText(null);
            setGraphic(gridPane);
        }

    }
}