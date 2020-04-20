package at.fhv.teamb.symphoniacus.domain; Mockito.mock(DutyEntity.class);

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformances;
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
        DutyCategory dc = new DutyCategory();
        dc.setIsRehearsal(true);
        dc.setType("Konzert-Probe");
        Mockito.when(de.getDutyCategory()).thenReturn(dc);

        SeriesOfPerformances sp = new SeriesOfPerformances();
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

        DutyCategory dc = new DutyCategory();
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
