package at.fhv.teamb.symphoniacus.domain;

/**
 * The State enum indicated whether a object has been persisted in the database or
 * has unsaved changes.
 *
 * @author Tobias Moser
 * @author Dominic Luidold
 */
public enum PersistenceState {
    /**
     * Indicates that the domain object has been persisted and/or equals the state in the database.
     */
    PERSISTED,

    /**
     * Indicates that the domain object has been edited and differs from the state in the database.
     */
    EDITED
}
