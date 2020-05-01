package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.presentation.MainController;
import at.fhv.teamb.symphoniacus.presentation.MasterController;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task that processes the GUI login.
 *
 * @author Valentin Goronjic
 * @see LoadingAnimationTask
 */
public class SwitchSceneTask extends LoadingAnimationTask<MainController> {

    private static final Logger LOG = LogManager.getLogger(SwitchSceneTask.class);
    private AnchorPane root;
    private ResourceBundle bundle;

    public SwitchSceneTask(
        AnchorPane root,
        ResourceBundle bundle
    ) {
        super(root);
        this.root = root;
        this.bundle = bundle;
    }

    @Override
    protected MainController call() throws Exception {
        super.call();
        MainController controller = MasterController.<MainController>switchSceneTo(
            "/view/mainWindow.fxml",
            this.bundle,
            this.root
        );
        return controller;
    }
}