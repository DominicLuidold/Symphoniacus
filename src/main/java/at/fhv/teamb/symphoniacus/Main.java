package at.fhv.teamb.symphoniacus;

import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Main class for everything in the world.
     *
     * @param args A string array that will most-likely be empty for ad infinitum
     */
    public static void main(String[] args) {
        try {
            /*
            SeriesOfPerformancesEntity series = new SeriesOfPerformancesEntity();
            series.setDescription("Nino Danijel On ICE");
            series.setStartDate(LocalDate.of(2020,6,1));
            series.setEndDate(LocalDate.of(2020,6,10));
            series.setIsTour(false);

            MusicalPieceDao mdao = new MusicalPieceDao();
            Set<MusicalPieceEntity> pieces = new LinkedHashSet<>();

            pieces.add((mdao.find(1)).get());
            pieces.add((mdao.find(2)).get());
            pieces.add((mdao.find(3)).get());

            series.setMusicalPieces(pieces);
            InstrumentationDao instDao = new InstrumentationDao();
            Set<InstrumentationEntity> instrumentations = instDao.getInstrumentationsToMusicalPieces(pieces);
            series.setInstrumentations(instrumentations);

            SeriesOfPerformancesDao dao = new SeriesOfPerformancesDao();
            dao.persist(series);
            System.out.println();

             */
            MainGui.main(args);
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
