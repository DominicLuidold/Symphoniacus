package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;


/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISection {
    int getSectionId();
    String getSectionShortcut();
    String getDescription();

    /**
     * @return Returns unmodifiable collection of duty positions by section ID.
     */
    Collection<IDutyPosition> getIDutyPositions();

    /**
     * @return Returns unmodifiable collection of musicians by section ID.
     */
    Collection<IMusician> getIMusicians();

    /**
     * @return Returns unmodifiable collection of section instrumentations by section ID.
     */
    Collection<ISectionInstrumentation> getISectionInstrumentations();

    /**
     * @return Returns unmodifiable collection of section monthly schedules by section ID.
     */
    Collection<ISectionMonthlySchedule> getISectionMonthlySchedules();

    /**
     * @return Returns unmodifiable collection of section instrument categories by section ID.
     */
    Collection<ISectionInstrumentCategory> getISectionInstrumentCategories();

    /**
     * Checks how many section principals are required for this category of duty
     * @param dutyCategoryDescription specified duty category description
     * @return Returns amount of required section principals
     */
    int getCountOfRequiredSectionPrincipals(String dutyCategoryDescription);
}
