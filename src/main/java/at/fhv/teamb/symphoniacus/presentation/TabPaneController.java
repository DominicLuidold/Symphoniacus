package at.fhv.teamb.symphoniacus.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.StatusBar;

public class TabPaneController implements Initializable {
    @FXML
    public AnchorPane calendar;

    @FXML
    private StatusBar statusBar;

    private Label statusTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MasterController mc = MasterController.getInstance();
        mc.setStatusBar(this.statusBar);

        HBox box = new HBox();
        box.setSpacing(10);

        statusTextField = new Label();
        statusTextField.setText("Loaded");
        statusTextField.textProperty().addListener(
            it -> statusBar.setText(statusTextField.getText())
        );

        box.getChildren().add(statusTextField);

        this.statusBar.getLeftItems().add(box);
        this.statusBar.setText("");
    }

    public Label getStatusTextField() {
        return statusTextField;
    }

    public void setStatusTextField(Label statusTextField) {
        this.statusTextField = statusTextField;
    }
}
