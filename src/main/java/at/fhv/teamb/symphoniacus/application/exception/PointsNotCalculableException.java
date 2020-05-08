package at.fhv.teamb.symphoniacus.application.exception;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;

/**
 * The PointsNotCalculableException is used to signalize that the requested {@link Points} for
 * a {@link Musician} cannot be calculated, posing a risk of an illegal state.
 *
 * @author Dominic Luidold
 */
public class PointsNotCalculableException extends Exception {
    private static final long serialVersionUID = -6768744190379153626L;

    /**
     * Constructs a {@code PointsNotCalculableException} with {@code null}
     * as its error detail message.
     */
    public PointsNotCalculableException() {
        super();
    }

    /**
     * Constructs a {@code PointsNotCalculableException} with the specified detail message.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PointsNotCalculableException(String message) {
        super(message);
    }
}