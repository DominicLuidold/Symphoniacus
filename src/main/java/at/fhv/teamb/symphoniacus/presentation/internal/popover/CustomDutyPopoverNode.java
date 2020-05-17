package at.fhv.teamb.symphoniacus.presentation.internal.popover;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.application.PointsManager;
import at.fhv.teamb.symphoniacus.application.SectionManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.PopOver;

public class CustomDutyPopoverNode extends PopOverContentPane {

    private static final Logger LOG = LogManager.getLogger(CustomDutyPopoverNode.class);

    private Entry<?> entry;
    private Duty duty;
    private SectionDto section;
    private DutyPopoverController popoverController;
    private PointsManager pointsManager;

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

    private int getPointsOfDuty(Duty duty) {
        if (this.pointsManager == null) {
            this.pointsManager = new PointsManager();
        }
        Points p = this.pointsManager.getPointsOfDuty(duty.getEntity());
        LOG.debug("Calculated Points of duty: {}", p.getValue());

        return p.getValue();
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
                new FXMLLoader(
                    getClass()
                        .getResource("/view/customDutyPopover.fxml"),
                    bundle
                );

            try {
                Parent root = loader.load();
                setHeader(null);
                setCenter(root);

                this.popoverController = loader.getController();
                this.popoverController.setTitleText(this.duty.getTitle());
                this.popoverController.setDescriptionText(this.duty.getEntity().getDescription());

                int points = getPointsOfDuty(this.duty);
                this.popoverController.setPointsText(Integer.toString(points));

                if (this.duty.getEntity().getSeriesOfPerformances() != null) {
                    Set<IInstrumentationEntity> instrumentationSet =
                        this.duty.getEntity().getSeriesOfPerformances().getInstrumentations();
                    List<Label> instrumentations = new LinkedList<>();

                    for (IInstrumentationEntity ie : instrumentationSet) {
                        instrumentations.add(new Label(ie.getName()));
                    }
                    this.popoverController.setInstrumentationText(instrumentations);
                }
            } catch (IOException e) {
                LOG.error(e);
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
                    this.duty,
                    section
                );
            if (asi.isPresent()) {
                boolean ready = true;
                for (DutyPosition dp : asi.get().getDuty().getDutyPositions()) {
                    if (dp.getAssignedMusician().isEmpty()) {
                        ready = false;
                        break;
                    }
                }
                this.popoverController.setStatusSection(ready, section.getSectionId());
            }

        }

        this.popoverController.disableEditDutyBtn();
        this.popoverController.disableEditScheduleBtn();
    }

    /**
     * Load details for Dutyscheduler.
     */
    public void loadDutySchedulerProperties() {
        this.popoverController.setDuty(this.duty);
        this.popoverController.setSection(this.section);
        this.popoverController.removeSections();
        this.popoverController.removeEditDutyBtn();

        DutyScheduleManager dutyScheduleManager = new DutyScheduleManager();
        Optional<ActualSectionInstrumentation> asi = dutyScheduleManager
            .getInstrumentationDetails(
                this.duty,
                this.section
            );
        List<Label> ldps = new LinkedList<>();

        if (asi.isPresent()) {
            List<DutyPosition> dps = asi.get().getDuty().getDutyPositions();
            List<MusicalPiece> pieces = asi.get().getDuty().getMusicalPieces();

            for (MusicalPiece mp : pieces) {
                Label musicalPiece = new Label(mp.toString() + ": ");
                musicalPiece.setStyle("-fx-font-weight: bold");
                ldps.add(musicalPiece);

                for (DutyPosition dp : dps) {
                    if (
                        dp.getEntity()
                            .getInstrumentationPosition()
                            .getInstrumentation()
                            .getMusicalPiece()
                            .getName()
                            .equals(mp.getEntity().getName())
                    ) {
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
                ldps.add(new Label());
            }
        }
        this.popoverController.setInstrumentationStatus(ldps);
    }
}
