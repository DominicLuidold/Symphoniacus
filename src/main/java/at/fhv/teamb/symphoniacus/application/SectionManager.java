package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISectionDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager for Section.
 *
 * @author Tobias Moser
 */
public class SectionManager {
    private static final Logger LOG = LogManager.getLogger(MusicianManager.class);
    private ISectionDao sectionDao;

    public SectionManager() {
        this.sectionDao = new SectionDao();
    }

    /**
     * Will return a list of all Section as Dtos.
     * @return List of all Sections
     */
    public List<SectionDto> getAll() {
        List<ISectionEntity> sectionEntities = this.sectionDao.getAll();
        List<SectionDto> sections = new LinkedList<>();
        for (ISectionEntity se : sectionEntities) {
            SectionDto.SectionDtoBuilder dtob = new SectionDto.SectionDtoBuilder(se.getSectionId());
            dtob.withSectionShortcut(se.getSectionShortcut());
            dtob.withDescription(se.getDescription());
            dtob.withSectionMonthlySchedules(se.getSectionMonthlySchedules());
            dtob.withMusicians(se.getMusicians());
            dtob.withDutyPositions(se.getDutyPositions());
            dtob.withSectionInstrumentations(se.getSectionInstrumentations());
            sections.add(dtob.build());
        }
        return sections;
    }
}
