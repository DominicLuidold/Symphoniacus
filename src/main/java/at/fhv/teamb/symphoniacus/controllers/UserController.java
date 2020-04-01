package at.fhv.teamb.symphoniacus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;


public class UserController {


    @FXML
    private TextFlow txtSection;

    @FXML
    private MenuItem user_logout;

    @FXML
    private Circle profile_avatar;


    @FXML
    void handleUserLogout(ActionEvent event) {
        System.out.println("test");
    }


}
