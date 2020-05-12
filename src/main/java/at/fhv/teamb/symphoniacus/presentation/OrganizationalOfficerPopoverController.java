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

    public void setStatusSection(Boolean isReady, int sectionnum) {
        Label section = null;
        switch (sectionnum) {
            case 1:
                section = this.section1Text;
                break;
            case 2:
                section = this.section2Text;
                break;
            case 3:
                section = this.section3Text;
                break;
            case 4:
                section = this.section4Text;
                break;
            case 5:
                section = this.section5Text;
                break;
            case 6:
                section = this.section6Text;
                break;
            case 7:
                section = this.section7Text;
                break;
            case 8:
                section = this.section8Text;
                break;
        }

        if (section != null) {
            StringBuilder sectionText = new StringBuilder(section.getText());
            if (isReady) {
                sectionText.insert(0, "✅ ");
                section.setStyle("-fx-text-fill: green");

            } else {
                sectionText.insert(0, "✗ ");
                section.setStyle("-fx-text-fill: red");
            }
            section.setText(sectionText.toString());
        }
    }

}
