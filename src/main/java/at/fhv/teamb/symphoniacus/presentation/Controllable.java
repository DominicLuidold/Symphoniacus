package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;

/**
 * This interface defines methods for showing and hiding views with a centralized controller.
 *
 * @deprecated in favor of {@link Parentable} interface
 */
@Deprecated(forRemoval = false, since = "01.05.2020")
public interface Controllable {

    /**
     * Registers the controller.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    @Deprecated
    void registerController();

    /**
     * Show a given view.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    @Deprecated
    void show();

    /**
     * Hide a given view.
     *
     * @deprecated in favor of {@link Parentable} interface methods
     */
    @Deprecated
    void hide();
}
