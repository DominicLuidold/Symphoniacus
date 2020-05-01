package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.Set;

public class SeriesOfPerformancesManager {
    private MusicalPieceDao musicalPieceDao;
    private InstrumentationDao instrumentationDao;
    private SeriesOfPerformancesDao seriesOfPerformancesDao;


    public SeriesOfPerformancesManager() {
        this.musicalPieceDao = new MusicalPieceDao();
    }

    public Set<MusicalPieceEntity> getAllMusicalPieces() {
        return this.musicalPieceDao.getAllMusicalPieces();
    }
}
