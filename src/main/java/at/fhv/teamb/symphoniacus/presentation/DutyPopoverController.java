package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.Duty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DutyPopoverController  implements Initializable {
    @FXML
    private Label titleText;

    @FXML
    private Label descriptionText;

    @FXML
    private Label pointsText;

    @FXML
    private VBox instrumentationsVBox;

    @FXML
    private VBox instrumentationStatus;

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

    @FXML
    private Button editScheduleBtn;

    @FXML
    private Button editDutyBtn;

    private Duty currentDuty;
    private SectionDto section;
    private DutyScheduleController dutyScheduleController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.editScheduleBtn.setOnAction(e -> {
            MasterController mc = MasterController.getInstance();

            if (mc.get("CalendarController") instanceof DutySchedulerCalendarController) {
                DutySchedulerCalendarController cc =
                    (DutySchedulerCalendarController) mc.get("CalendarController");
                cc.hide();
            }

            if (this.dutyScheduleController == null) {
                if (mc.get("DutyScheduleController") instanceof DutyScheduleController) {
                    this.dutyScheduleController =
                        (DutyScheduleController) mc.get("DutyScheduleController");
                }
            }

            this.dutyScheduleController.setDuty(currentDuty);
            this.dutyScheduleController.setSection(this.section);
            this.dutyScheduleController.show();
        });
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

    public void setInstrumentationStatus(List<Label> instrumentationStatus) {
        this.instrumentationStatus.getChildren().addAll(instrumentationStatus);
    }

    public void setDuty(Duty duty) {
        this.currentDuty = duty;
    }

    public void setSection(SectionDto section) {
        this.section = section;
    }

    /**
     * This Function set the color for the Status view of every Section.
     * @param isReady True if in the Sectioninstrumentatin is every Position assinged to a Position
     * @param sectionNum Number of section from 1 to 8
     */
    public void setStatusSection(Boolean isReady, int sectionNum) {
        Label section = null;
        switch (sectionNum) {
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
            default:
                break;
        }

        if (section != null) {
            StringBuilder sectionText = new StringBuilder(section.getText());
            if (isReady) {
                sectionText.insert(0, "\u2714 "); // Unicode character, ✔
                section.setStyle("-fx-text-fill: green");

            } else {
                sectionText.insert(0, "\u2718 "); // Unicode character, ✘
                section.setStyle("-fx-text-fill: red");
            }
            section.setText(sectionText.toString());
        }
    }

    public void removeSections() {
        this.instrumentationStatus.getChildren().clear();
    }

    public void disableEditScheduleBtn() {
        this.editScheduleBtn.setDisable(true);
    }

    public void disableEditDutyBtn() {
        this.editDutyBtn.setDisable(true);
    }

    public  void removeEditDutyBtn() {
        this.editDutyBtn.setVisible(false);
    }
}
