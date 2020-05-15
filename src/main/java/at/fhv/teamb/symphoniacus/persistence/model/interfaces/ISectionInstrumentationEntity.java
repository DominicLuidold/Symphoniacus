package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

/**
 * @author Valentin
 */
public interface ISectionInstrumentationEntity {

    Integer getSectionInstrumentationId();

    String getPredefinedSectionInstrumentation();

    void setInstrumentation(IInstrumentationEntity entity);
}
