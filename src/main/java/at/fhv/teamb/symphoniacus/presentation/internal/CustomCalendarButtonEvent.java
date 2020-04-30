package at.fhv.teamb.symphoniacus.presentation.internal;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * This class holds all custom event types built into CalendarFX skins.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class CustomCalendarButtonEvent extends Event {
    /**
     * This event is thrown when the "Forward" button is pressed.
     */
    public static final EventType<CustomCalendarButtonEvent> FORWARD_DUTY_ROSTER_EVENT
        = new EventType<>("ForwardDutyRoster");

    /**
     * This event is thrown when the "Publish" button is pressed.
     */
    public static final EventType<CustomCalendarButtonEvent> PUBLISH_DUTY_ROSTER_EVENT
        = new EventType<>("PublishDutyRoster");

    public CustomCalendarButtonEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
