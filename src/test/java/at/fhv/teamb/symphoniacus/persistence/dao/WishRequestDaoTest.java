package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WishRequestDaoTest {
    private static final Logger LOG = LogManager.getLogger(WishRequestDaoTest.class);
    private PositiveWishDao pdao;
    private NegativeDutyWishDao ndutydao;
    private NegativeDateWishDao ndatedao;

    @BeforeAll
    void initialize() {
        this.pdao = new PositiveWishDao();
        this.ndutydao = new NegativeDutyWishDao();
        this.ndatedao = new NegativeDateWishDao();
    }

    @Test
    void getAllWishes_ShouldNotReturnNullAndWishesAreValid() {
        Random rand = new Random();
        Integer rndId = rand.nextInt(100) + 1;
        DutyDao dutyDao = new DutyDao();
        Optional<DutyEntity> realDuty = dutyDao.find(rndId);

        if (realDuty.isPresent()) {
            List<WishRequestable> positiveWishes = pdao.getAllPositiveWishes(realDuty.get());
            List<WishRequestable> negativeDutyWishes = ndutydao.getAllNegativeDutyWishes(realDuty.get());
            List<WishRequestable> negativeDateWishes = ndatedao.getAllNegativeDateWishes(realDuty.get());

            Assertions.assertNotNull(positiveWishes);
            Assertions.assertNotNull(negativeDutyWishes);
            Assertions.assertNotNull(negativeDateWishes);
        }

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