package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import static javafx.application.Application.launch;

public class UserTable extends Application {


    private UserTableWindowController _userTableWindowController;


    public void setParameter(UserTableWindowController userTableWindowController){
        _userTableWindowController = userTableWindowController;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        //Background work
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserTable.fxml"));
                            Parent root = loader.load();
                            _userTableWindowController = loader.getController();
                            _userTableWindowController.setLoggedInUserName(LoginWindowController.getLoggedInUser());


                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        Locale.setDefault(Locale.ENGLISH);
                                        primaryStage.setTitle("User Management");
                                        primaryStage.getIcons().add(new Image(UserTable.class.getResourceAsStream("/orchestraria_icon.png")));
                                        Scene scene = new Scene(root);
                                        primaryStage.setScene(scene);
                                        primaryStage.setOnHidden(e -> Platform.exit());

                                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                                        primaryStage.setY(5);
                                        double width = 738;

                                        primaryStage.setX((primScreenBounds.getWidth() - width) / 2);

                                        primaryStage.show();

                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                            latch.await();
                            //Keep with the background work


                            _userTableWindowController.init();


                        }catch ( IOException | InterruptedException ex){
                            ex.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };
        service.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

