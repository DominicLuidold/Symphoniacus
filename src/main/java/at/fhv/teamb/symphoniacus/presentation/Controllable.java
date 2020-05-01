package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;

/**
 * This interface defines methods for showing and hiding views with a centralized controller.
 *
 * @deprecated in favor of {@link Parentable} interface
 */
public interface Controllable {

    /**
     * Registers the controller.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    void registerController();

    /**
     * Show a given view.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    void show();

    /**
     * Hide a given view.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    void hide();
}
