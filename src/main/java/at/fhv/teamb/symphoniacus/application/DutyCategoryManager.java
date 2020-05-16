package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryChangeLogDto;
import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryDto;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.domain.DutyCategoryChangelog;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyCategoryManager {
    private static final Logger LOG = LogManager.getLogger(DutyCategoryManager.class);
    private final IDutyCategoryDao categoryDao;
    private List<DutyCategory> dutyCategories;
    private List<IDutyCategoryEntity> dutyCategoryEntities;

    /**
     * Initializes the DutyCategoryManager (usage for Team B only).
     */
    public DutyCategoryManager() {
        this.categoryDao = new DutyCategoryDao();
    }

    /**
     * Initializes the DutyCategoryManager (usage for Team C only).
     *
     * @param categoryDao The DutyCategoryDao used in this manager
     */
    public DutyCategoryManager(IDutyCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * Returns all {@link DutyCategory} objects.
     *
     * @return A List of duty category objects
     */
    public List<DutyCategoryDto> getDutyCategories() {
        // Fetch duty categories from database if not present
        if (this.dutyCategories == null) {
            // Get duty category entities from database
            this.dutyCategories = new LinkedList<>();
            if (this.dutyCategoryEntities == null) {
                this.dutyCategoryEntities = this.categoryDao.getAll();
            }

            // Convert duty category entities to domain objects
            for (IDutyCategoryEntity entity : this.dutyCategoryEntities) {
                // Convert duty category changelog entities to domain objects
                List<DutyCategoryChangelog> changelogList = new LinkedList<>();
                for (IDutyCategoryChangelogEntity changelogEntity :
                    entity.getDutyCategoryChangelogs()
                ) {
                    changelogList.add(new DutyCategoryChangelog(changelogEntity));
                }

                // Create domain object
                this.dutyCategories.add(new DutyCategory(entity, changelogList));
            }
        }
        return convertCategoriesToDto(this.dutyCategories);
    }

    private List<DutyCategoryDto> convertCategoriesToDto(List<DutyCategory> cats) {
        List<DutyCategoryDto> dutyCategoryDtos = new LinkedList<>();
        for (DutyCategory d : cats) {
            DutyCategoryDto dc = new DutyCategoryDto
                .DutyCategoryDtoBuilder(d.getEntity().getDutyCategoryId())
                .withType(d.getEntity().getType())
                .withChangeLogs(convertChangeLogToDto(d.getChangelogList())).build();
            dutyCategoryDtos.add(dc);
        }
        return dutyCategoryDtos;
    }

    private List<DutyCategoryChangeLogDto> convertChangeLogToDto(
        List<DutyCategoryChangelog> changelogList
    ) {
        List<DutyCategoryChangeLogDto> changeLog = new LinkedList<>();
        for (DutyCategoryChangelog cl : changelogList) {
            DutyCategoryChangeLogDto clDto = new DutyCategoryChangeLogDto
                .DutyCategoryChangeLogDtoBuilder(cl.getEntity().getDutyCategoryChangelogId())
                .withPoints(cl.getEntity().getPoints())
                .withDutyCategory(convertCategoryEntityToDto(cl.getEntity().getDutyCategory()))
                .withStartDate(cl.getEntity().getStartDate())
                .build();
            changeLog.add(clDto);
        }
        return changeLog;
    }

    /**
     * Persists the changes made to the {@link DutyCategory} object to the database.
     *
     * <p>The method will subsequently change the {@link PersistenceState} of the object
     * from {@link PersistenceState#EDITED} to {@link PersistenceState#PERSISTED}, provided
     * that the database update was successful.
     *
     * @param dutyCategory The duty category to persist
     */
    public void persist(DutyCategory dutyCategory) {
        Optional<IDutyCategoryEntity> persisted = this.categoryDao.update(dutyCategory.getEntity());

        if (persisted.isPresent()) {
            dutyCategory.setPersistenceState(PersistenceState.PERSISTED);
            LOG.debug(
                "Persisted duty category {{}, '{}'}",
                dutyCategory.getEntity().getDutyCategoryId(),
                dutyCategory.getEntity().getType()
            );
        } else {
            LOG.error(
                "Could not persist duty category {{}, '{}'}",
                dutyCategory.getEntity().getDutyCategoryId(),
                dutyCategory.getEntity().getType()
            );
        }
    }

    private DutyCategoryDto convertCategoryEntityToDto(IDutyCategoryEntity dutyCat) {
        return new DutyCategoryDto.DutyCategoryDtoBuilder(dutyCat.getDutyCategoryId())
            .withPoints(dutyCat.getPoints()).withType(dutyCat.getType()).build();
    }
}
