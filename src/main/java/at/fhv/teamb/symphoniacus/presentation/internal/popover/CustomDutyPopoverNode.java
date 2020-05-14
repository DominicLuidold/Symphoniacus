package at.fhv.teamb.symphoniacus.presentation.internal.popover;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.application.SectionManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.presentation.DutyPopoverController;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.PopOverContentPane;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;

public class CustomDutyPopoverNode extends PopOverContentPane {
    private Entry<?> entry;
    private Duty duty;
    private SectionDto section;
    private DutyPopoverController popoverController;

    /**
     * Custom Popover for Dutyscheduler.
     */
    public CustomDutyPopoverNode(
        PopOver popOver,
        DateControl dateControl,
        Node node,
        Entry<?> entry,
        SectionDto section
    ) {
        if (entry.getUserObject() != null) {
            this.duty = (Duty) entry.getUserObject();
            this.section = section;
            loadBasePorperties();
            loadDutySchedulerProperties();
        }
    }

    /**
     * Custom Popover for OrganizationOfficer.
     */
    public CustomDutyPopoverNode(
        PopOver popOver,
        DateControl dateControl,
        Node node,
        Entry<?> entry
    ) {
        if (entry.getUserObject() != null) {
            this.duty = (Duty) entry.getUserObject();
            loadBasePorperties();
            loadOrganizationOfficerPoperties();
        }
    }

    /**
     * Load the Duty Details: Title, Discription, Points and Instrumentation.
     */
    public void loadBasePorperties() {
        if (this.duty != null) {
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            FXMLLoader loader =
                new FXMLLoader(getClass()
                    .getResource("/view/customDutyPopover.fxml"), bundle);

            try {
                Parent root = loader.load();
                setHeader(null);
                setCenter(root);

                this.popoverController = loader.getController();
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

    /**
     * Load details for Organization Officer.
     */
    public void loadOrganizationOfficerPoperties() {
        List<SectionDto> sections = new SectionManager().getAll();
        DutyScheduleManager dutyScheduleManager = new DutyScheduleManager();

        for (SectionDto section : sections) {
            Optional<ActualSectionInstrumentation> asi =
                dutyScheduleManager.getInstrumentationDetails(
                    duty,
                    section
                );
            if (asi.isPresent()) {
                Boolean ready = true;
                for (DutyPosition dp : asi.get().getDuty().getDutyPositions()) {
                    if (dp.getAssignedMusician().isEmpty()) {
                        ready = false;
                        break;
                    }
                }
                popoverController.setStatusSection(ready, section.getSectionId());
            }

        }

        popoverController.disableEditDutyBtn();
        popoverController.disableEditScheduleBtn();

    }

    /**
     * Load details for Dutyscheduler.
     */
    public void loadDutySchedulerProperties() {
        popoverController.setDuty(duty);
        popoverController.setSection(section);
        popoverController.removeSections();
        popoverController.removeEditDutyBtn();

        DutyScheduleManager dutyScheduleManager = new DutyScheduleManager();
        Optional<ActualSectionInstrumentation> asi =
            dutyScheduleManager.getInstrumentationDetails(
                duty,
                section
            );
        List<Label> ldps = new LinkedList<>();

        if (asi.isPresent()) {
            List<DutyPosition> dps = asi.get().getDuty().getDutyPositions();
            for (DutyPosition dp : dps) {
                if (dp.getAssignedMusician().isPresent()) {
                    Label l = new Label(
                        "\u2714 " + dp.getEntity() // Unicode character, ✔
                            .getInstrumentationPosition().getPositionDescription()
                            + ": " + dp.getAssignedMusician().get().getFullName());
                    l.setStyle("-fx-text-fill: green");
                    ldps.add(l);

                } else {
                    Label l = new Label("\u2718 " + dp.getEntity() // Unicode character, ✘
                        .getInstrumentationPosition().getPositionDescription());
                    l.setStyle("-fx-text-fill: red");
                    ldps.add(l);
                }

            }

        }
        popoverController.setInstrumentationStatus(ldps);
    }
}
