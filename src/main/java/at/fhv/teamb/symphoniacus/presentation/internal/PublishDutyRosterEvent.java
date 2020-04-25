package at.fhv.teamb.symphoniacus.presentation.internal;

import javafx.event.Event;
import javafx.event.EventType;

public class PublishDutyRosterEvent extends Event {
    public static final EventType<PublishDutyRosterEvent> PUBLISH_DUTY_ROSTER_EVENT_EVENT_TYPE
        = new EventType<>("PublishDutyRoster");

    public PublishDutyRosterEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
