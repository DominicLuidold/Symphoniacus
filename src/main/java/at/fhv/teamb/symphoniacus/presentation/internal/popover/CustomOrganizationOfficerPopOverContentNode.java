package at.fhv.teamb.symphoniacus.presentation.internal.popover;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.presentation.OrganizationalOfficerPopoverController;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.PopOverContentPane;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import org.controlsfx.control.PopOver;

public class CustomOrganizationOfficerPopOverContentNode extends PopOverContentPane {

    /**
     * Custom Popover for OrganizationOfficer.
     */
    public CustomOrganizationOfficerPopOverContentNode(PopOver popOver, DateControl dateControl, Node node, Entry<?> entry) {

        if (entry instanceof Entry) {
            Duty orchestraEntry = (Duty) entry.getUserObject();
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            FXMLLoader loader =
                new FXMLLoader(getClass()
                    .getResource("/view/organizationalOfficerPopOver.fxml"), bundle);

            try {
                Parent root = loader.load();
                OrganizationalOfficerPopoverController popOverMusician = loader.getController();

                setHeader(null);
                setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
