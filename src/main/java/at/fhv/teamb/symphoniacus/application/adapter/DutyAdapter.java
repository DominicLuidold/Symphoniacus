package at.fhv.teamb.symphoniacus.application.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDuty;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableSeriesOfPerformances;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class DutyAdapter implements IntegratableDuty {
    private final IDutyEntity duty;

    public DutyAdapter(IDutyEntity duty) {
        this.duty = duty;
    }

    @Override
    public boolean isRehearsal() {
        return this.duty.getDutyCategory().getIsRehearsal();
    }

    @Override
    public IntegratableSeriesOfPerformances getSeriesOfPerformances() {
        return new SeriesOfPerformancesAdapter(this.duty.getSeriesOfPerformances());
    }

    @Override
    public String getDutyCategoryDescription() {
        return this.duty.getDutyCategory().getType();
    }

    @Override
    public String getDescription() {
        return this.duty.getDescription();
    }

    @Override
    public String getMusicalPieceString() {
        String musicalPieceStr = "-";
        int count = 0;
        if (this.duty.getSeriesOfPerformances() != null) {
            for (IMusicalPieceEntity musicalPiece :
                this.duty.getSeriesOfPerformances().getMusicalPieces()
            ) {
                if (count < 1) {
                    musicalPieceStr = musicalPiece.getName();
                }
                count++;
            }

            if (count > 1) {
                musicalPieceStr += " + " + (count - 1) + "other";
                if (count > 2) {
                    musicalPieceStr += "s";
                }
            }
        }
        return musicalPieceStr;
    }

    @Override
    public String getComposerString() {
        Set<String> composers = new HashSet<>();
        String composerStr = "-";
        if (this.duty.getSeriesOfPerformances() != null) {
            for (IMusicalPieceEntity musicalPiece :
                this.duty.getSeriesOfPerformances().getMusicalPieces()
            ) {
                if (composers.isEmpty()) {
                    composerStr = musicalPiece.getComposer();
                }
                composers.add(musicalPiece.getComposer());
            }

            if (composers.size() > 1) {
                composerStr += " + " + (composers.size() - 1) + "other";
                if (composers.size() > 2) {
                    composerStr += "s";
                }
            }
        }
        return composerStr;
    }

    @Override
    public LocalDateTime getStart() {
        return this.duty.getStart();
    }

    @Override
    public LocalDateTime getEnd() {
        return this.duty.getEnd();
    }

    @Override
    public String getInstrumentationString() {
        String instrumentationStr = "-";
        Set<String> instrumentations = new HashSet<>();
        if (this.duty.getSeriesOfPerformances() != null) {
            for (IInstrumentationEntity instrumentation :
                this.duty.getSeriesOfPerformances().getInstrumentations()
            ) {
                if (instrumentations.isEmpty()) {
                    instrumentationStr = instrumentation.getName();
                }
                instrumentations.add(instrumentation.getName());
            }

            if (instrumentations.size() > 1) {
                instrumentationStr += " + " + (instrumentations.size() - 1) + "other";
                if (instrumentations.size() > 2) {
                    instrumentationStr += "s";
                }
            }
        }
        return instrumentationStr;
    }

    @Override
    public int getDutyId() {
        return this.duty.getDutyId();
    }
}
