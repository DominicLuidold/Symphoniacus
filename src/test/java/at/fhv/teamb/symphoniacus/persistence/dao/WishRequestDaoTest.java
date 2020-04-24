package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WishRequestDaoTest {
    private static final Logger LOG = LogManager.getLogger(WishRequestDao.class);
    private WishRequestDao dao;

    @BeforeAll
    void initialize() {
        this.dao = new WishRequestDao();
    }

    @Test
    void getAllWishes_ShouldNotReturnNullAndWishesAreValid() {
        DutyEntity mockedDuty = Mockito.mock(DutyEntity.class);
        mockedDuty.setDutyId(Mockito.anyInt());

        List<PositiveWishEntity> positiveWishes = dao.getAllPositiveWishes(mockedDuty);
        List<NegativeDutyWishEntity> negativeDutyWishes = dao.getAllNegativeDutyWishes(mockedDuty);
        List<NegativeDateWishEntity> negativeDateWishes = dao.getAllNegativeDateWishes(mockedDuty);

        Assertions.assertNotNull(positiveWishes);
        Assertions.assertNotNull(negativeDutyWishes);
        Assertions.assertNotNull(negativeDateWishes);


        // TODO - kann erst gestestet werden, man ein Duty anhand einer ID aus dem DutyDao
        //  holen kann, denn für die ganzen vergleiche wird ein echtes valides Duty benötigt
        /*
        if (!positiveWishes.isEmpty()) {
            for (PositiveWishEntity wish : positiveWishes) {
                Assertions.assertEquals(wish.getSeriesOfPerformances().getSeriesOfPerformancesId(),
                    mockedDuty.getSeriesOfPerformances().getInstrumentationId());
            }
        } else if (!negativeDutyWishes.isEmpty()) {
            for (NegativeDutyWishEntity wish : negativeDutyWishes) {
                Assertions.assertEquals(wish.getSeriesOfPerformances().getSeriesOfPerformancesId(),
                    mockedDuty.getSeriesOfPerformances().getSeriesOfPerformancesId());
            }
        } else if (!negativeDateWishes.isEmpty()) {
            for (NegativeDateWishEntity wish : negativeDateWishes) {
                List<MonthlyScheduleEntity> monthlySchedules = wish.getMonthlySchedules();
                if (!monthlySchedules.isEmpty()) {
                    for (MonthlyScheduleEntity monthlySchedule : monthlySchedules) {
                        Assertions.assertEquals(monthlySchedule.getMonthlyScheduleId(),
                            mockedDuty.getWeeklySchedule().getMonthlySchedule()
                                .getMonthlyScheduleId());
                    }
                }
            }
        }
         */
    }
}