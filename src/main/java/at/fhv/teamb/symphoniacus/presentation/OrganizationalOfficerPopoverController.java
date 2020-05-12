package at.fhv.teamb.symphoniacus.presentation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class OrganizationalOfficerPopoverController implements Initializable {
    @FXML
    private Label titleText;

    @FXML
    private Label descriptionText;

    @FXML
    private Label pointsText;

    @FXML
    private VBox instrumentationsVBox;

    @FXML
    private Label section1Text;

    @FXML
    private Label section2Text;

    @FXML
    private Label section3Text;

    @FXML
    private Label section4Text;

    @FXML
    private Label section5Text;

    @FXML
    private Label section6Text;

    @FXML
    private Label section7Text;

    @FXML
    private Label section8Text;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText.setText(descriptionText);
    }

    public void setPointsText(String pointsText) {
        this.pointsText.setText(pointsText);
    }

    public void setInstrumentationText(List<Label> instrumentations) {
        this.instrumentationsVBox.getChildren().addAll(instrumentations);
    }
}
