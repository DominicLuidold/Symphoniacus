package at.fhv.teamb.symphoniacus.presentation.internal;

/**
 * Interface which allows a GUI controller to access it's parent controller.
 *
 * @author Valentin Goronjic
 */
public interface Parentable<T> {
    void setParentController(T controller);

    T getParentController();
}
