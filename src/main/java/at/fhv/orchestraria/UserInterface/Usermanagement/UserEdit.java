package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.domain.Imodel.IUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Locale;

public class UserEdit extends Application {


    private UserEditWindowController _userEditWindowController;
    private IUser _ue;
    private int listviewindex;
    private UserTableWindowController _parentTreeTable;
    private boolean isNewUser;

    public void setParameter(UserEditWindowController userEditWindowController, IUser ue, int listindex, UserTableWindowController parentTreeTable, boolean _isNewUser){
        _userEditWindowController = userEditWindowController;
        _ue = ue;
        listviewindex = listindex;
        _parentTreeTable = parentTreeTable;
        isNewUser = _isNewUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale.setDefault(Locale.ENGLISH);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserEdit.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Edit User");
        primaryStage.getIcons().add(new Image(UserTable.class.getResourceAsStream("/orchestraria_icon.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setY(5);
        double width = 738;

        primaryStage.setX((primScreenBounds.getWidth() - width) / 2);

        _userEditWindowController = loader.getController();
        _userEditWindowController.setParameter(_ue, listviewindex, _parentTreeTable, isNewUser);
        _userEditWindowController.init();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

