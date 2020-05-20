package at.fhv.orchestraria.UserInterface.DutyAssignment;

import at.fhv.orchestraria.UserInterface.MainWindow.MainWindow;
import at.fhv.orchestraria.UserInterface.Roster.AssignmentRosterThread;
import at.fhv.orchestraria.UserInterface.Roster.OrchestraEntry;
import at.fhv.orchestraria.application.DutyAssignmentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class DutyAssignmentWindow extends Application {

    private DutyAssignmentController _dutyAssController;
    private OrchestraEntry _orchestraEntry;
    private DutyAssignmentWindowController _dutyAssiWinController;
    private AssignmentRosterThread _assRosterThread;


    public void setParameter(DutyAssignmentController dutyAssController, OrchestraEntry entry, AssignmentRosterThread assignmentRosterThread) {
        _dutyAssController = dutyAssController;
        _orchestraEntry = entry;
        _assRosterThread = assignmentRosterThread;

    }
//    ACTIVATE THIS FOR THE LOADING BAR TO WORK
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() {
//                        try {
//                            //Background work
//                            Locale.setDefault(Locale.ENGLISH);
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DutyAssignmentWindow.fxml"));
//                            Parent root = loader.load();
//
//
//                            final CountDownLatch latch = new CountDownLatch(1);
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        //FX stuff here
//
//                                        primaryStage.setTitle("Duty Assignment");
//                                        primaryStage.getIcons().add(new Image(MainWindow.class.getResourceAsStream("/orchestraria_icon.png")));
//                                        Scene scene = new Scene(root);
//                                        primaryStage.setScene(scene);
//
//                                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//                                        primaryStage.setY(5);
//                                        double width = 738;
//                                        primaryStage.setX((primScreenBounds.getWidth() - width) / 2);
//                                        primaryStage.show();
//
//                                    } finally {
//                                        latch.countDown();
//                                    }
//                                }
//                            });
//                            latch.await();
//                            //Keep on with the background work
//                            _dutyAssiWinController = loader.getController();
//                            _dutyAssiWinController.init(_dutyAssController, _orchestraEntry, _assRosterThread );
//
//                        }catch (IllegalStateException | IOException | InterruptedException ex){
//                            ex.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//            }
//        };
//        service.start();
//
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //Background work
            Locale.setDefault(Locale.ENGLISH);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DutyAssignmentWindow.fxml"));
            Parent root = loader.load();
            _dutyAssiWinController = loader.getController();


            primaryStage.setTitle("Duty Assignment");
            primaryStage.getIcons().add(new Image(MainWindow.class.getResourceAsStream(
                "/images/team-c/orchestraria_icon.png")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setY(5);
            double width = 738;
            primaryStage.setX((primScreenBounds.getWidth() - width) / 2);
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        _dutyAssiWinController.init(_dutyAssController, _orchestraEntry, _assRosterThread);

    }


    public static void main(String[] args) {
        launch(args);
    }


}
