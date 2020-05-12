package at.fhv.teamb.symphoniacus.presentation.internal.popover;

import at.fhv.teamb.symphoniacus.application.PointsManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.presentation.OrganizationalOfficerPopoverController;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.PopOverContentPane;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;

public class CustomOrganizationOfficerPopOverContentNode extends PopOverContentPane {

    /**
     * Custom Popover for OrganizationOfficer.
     */
    public CustomOrganizationOfficerPopOverContentNode(PopOver popOver,
                                                       DateControl dateControl,
                                                       Node node,
                                                       Entry<?> entry
    ) {

        if (entry instanceof Entry) {
            Duty duty = (Duty) entry.getUserObject();
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            FXMLLoader loader =
                new FXMLLoader(getClass()
                    .getResource("/view/organizationalOfficerPopOver.fxml"), bundle);

            try {
                Parent root = loader.load();
                setHeader(null);
                setCenter(root);

                OrganizationalOfficerPopoverController popoverController = loader.getController();
                popoverController.setTitleText(duty.getTitle());
                popoverController.setDescriptionText(duty.getEntity().getDescription());
                popoverController.setPointsText(duty
                    .getEntity()
                    .getDutyCategory()
                    .getPoints()
                    .toString()
                );

                Set<InstrumentationEntity> instrumentationSet =
                    duty.getEntity().getSeriesOfPerformances().getInstrumentations();
                List<Label> instrumentations = new LinkedList<>();
                for (InstrumentationEntity ie : instrumentationSet) {
                    instrumentations.add(new Label(ie.getName()));
                }
                popoverController.setInstrumentationText(instrumentations);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
