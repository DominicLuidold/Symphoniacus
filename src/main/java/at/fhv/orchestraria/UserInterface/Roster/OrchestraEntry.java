package at.fhv.orchestraria.UserInterface.Roster;


import at.fhv.orchestraria.domain.Imodel.IDuty;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDuty;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import static java.util.Objects.requireNonNull;

/**
 * A single Entry in the CalendarView that links the entry directly to the corresponding duty.
 * Responsible for collecting the information that is presented in day and week view.
 * @param <T> the type of the user object
 */
public class OrchestraEntry<T> extends Entry<T> {

    private IntegratableDuty _duty;

    public OrchestraEntry(IntegratableDuty duty){
        requireNonNull(duty);
        _duty = duty;
        String composer = duty.getComposerString();
        String musicalPiece = duty.getMusicalPieceString();
        String dutyDescription = duty.getDescription();

        if(duty.getSeriesOfPerformances() != null){
            if(duty.getSeriesOfPerformances().isTour()){

            }
        }


        //when the duty is not a musical event
        if(composer.equals("-") && musicalPiece.equals("-")){
            if(dutyDescription.equals("")){
                setTitle(duty.getDutyCategoryDescription());
            }else{
                setTitle(duty.getDutyCategoryDescription() + "\n"+ duty.getDescription());
            }
        }else{
            if(dutyDescription.equals("")){
                setTitle(duty.getDutyCategoryDescription() + "\n"+
                        duty.getMusicalPieceString()+ "\n" +duty.getComposerString());
            }else{
                setTitle(duty.getDutyCategoryDescription() +"\n"+ duty.getDescription() + "\n"+
                        duty.getMusicalPieceString()+ "\n" +duty.getComposerString());
            }
        }

        setInterval(new Interval(duty.getStart(), duty.getEnd()));

    }

    public void setDuty(IntegratableDuty duty) {
        _duty = duty;
    }

    public IntegratableDuty getDuty() {
        return _duty;
    }

    public String getDescription(){
        return  _duty.getDescription();
    }

    /*
    Converting LocalDateTime to Date in english dateFormat
     */
    public String getStartDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");

        Date date = Date.from(getStartAsLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(date);
    }


    public String getEndDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");

        Date date = Date.from(getEndAsLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(date);
    }

    

}
