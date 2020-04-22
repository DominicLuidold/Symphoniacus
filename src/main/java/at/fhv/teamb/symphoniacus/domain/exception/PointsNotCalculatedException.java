package at.fhv.teamb.symphoniacus.domain.exception;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;

/**
 * The PointsNotCalculatedException is used to signalize that the requested {@link Points} for
 * a {@link Musician} are not present.
 *
 * @author Dominic Luidold
 */
public class PointsNotCalculatedException extends Exception {
    private static final long serialVersionUID = 7047779152584394921L;

    /**
     * Constructs an {@code BadRequestException} with {@code null}
     * as its error detail message.
     */
    public PointsNotCalculatedException() {
        super();
    }

    /**
     * Constructs an {@code PointsNotCalculatedException} with the specified detail message.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PointsNotCalculatedException(String message) {
        super(message);
    }
}
