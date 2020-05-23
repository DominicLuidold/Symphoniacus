package at.fhv.orchestraria.UserInterface.Roster;

import com.calendarfx.model.CalendarSource;

public abstract class RosterThread implements Runnable{

    @Override
    public abstract void run();
    public abstract CalendarSource getSources();
}
