package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DutyTest {

    @Test
    void getTitle_shouldReturnACompositeTitleForSeries() {
        // Konzert-Probe for Aida at 17.05.2020
        // <CATEGORY> for <SERIES> (<DESCRIPTION>)
        DutyEntity de = Mockito.mock(DutyEntity.class);
        Mockito
            .when(de.getStart())
            .thenReturn(
                LocalDateTime.of(
                    2020,
                    05,
                    01,
                    11,
                    00,
                    00
                )
            );

        Mockito.when(de.getDescription()).thenReturn("TV");
        IDutyCategoryEntity dc = new DutyCategoryEntity();
        dc.setIsRehearsal(true);
        dc.setType("Konzert-Probe");
        Mockito.when(de.getDutyCategory()).thenReturn(dc);

        SeriesOfPerformancesEntity sp = new SeriesOfPerformancesEntity();
        sp.setDescription("Aida");
        Mockito.when(de.getSeriesOfPerformances()).thenReturn(sp);

        Duty duty = new Duty(de);
        Assertions.assertEquals("Konzert-Probe for Aida (TV)", duty.getTitle());
    }

    @Test
    void getTitle_shouldReturnACompositeTitleWithoutSeries() {
        // Juryservice at 17.05.2020
        // <CATEGORY> (<DESCRIPTION>)

        DutyEntity de = Mockito.mock(DutyEntity.class);
        Mockito
            .when(de.getStart())
            .thenReturn(
                LocalDateTime.of(
                    2020,
                    05,
                    17,
                    15,
                    00,
                    00
                )
            );

        IDutyCategoryEntity dc = new DutyCategoryEntity();
        dc.setIsRehearsal(false);
        dc.setType("Nicht-musikalischer Dienst");
        Mockito.when(de.getDutyCategory()).thenReturn(dc);

        Duty duty = new Duty(de);
        Assertions.assertEquals(
            "Nicht-musikalischer Dienst",
            duty.getTitle()
        );
    }

}
