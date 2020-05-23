package at.fhv.orchestraria.UserInterface.DutyAssignment;

import at.fhv.orchestraria.domain.Imodel.IDuty;
import at.fhv.orchestraria.domain.Imodel.IMusician;

import java.io.Serializable;


/**
 * A wrapper for the IMusician collect information concerning event and musician and to display
 * this information in a ListView.
 */
public class UIMusician implements Serializable {
    private IMusician _musician;
    private IDuty _duty;
    //private String _displayedString = null;
    private String _shortcut = null;
    private String _negativeDutyWish = null;
    private String _negativeDateWish = null;
    private String _positiveWish = null;
    private String _points = null;
    private boolean _isSectionPrincipal = false;


    public UIMusician(IMusician musician, IDuty duty){
        _musician = musician;
        _duty = duty;

        _shortcut = musician.getUser().getShortcut();
        if(musician.getNegativeDutyWish(duty).isPresent()){
            _negativeDutyWish = musician.getNegativeDutyWish(duty).get().getDescription();
        }
        if(musician.getNegativeDateWish(duty).isPresent()){
            _negativeDateWish = musician.getNegativeDateWish(duty).get().getDescription();
        }
        if(musician.getPositiveWish(duty).isPresent()){
            _positiveWish = musician.getPositiveWish(duty).get().getDescription();
        }

        StringBuilder pointsString = new StringBuilder();
        pointsString.append(_musician.getPointsOfMonth(_duty.getStart().toLocalDate()));
        pointsString.append('/');
        pointsString.append(_musician.getRequiredPointsOfMonth(_duty.getStart().toLocalDate()));

        pointsString.append(" ".repeat(Math.max(0, 9 - pointsString.length())));
        _points = pointsString.toString();
        if(musician.isSectionPrincipal()){
            _isSectionPrincipal = true;
        }

    }

    public IMusician getMusician() {
        return _musician;
    }

    public IDuty getDuty() {
        return _duty;
    }

    public String getShortcut() {
        return _shortcut;
    }

    public String getNegativeDutyWish() {
        return _negativeDutyWish;
    }

    public String getNegativeDateWish() {
        return _negativeDateWish;
    }

    public String getPositiveWish() {
        return _positiveWish;
    }

    public String getPoints() {
        return _points;
    }

    public boolean isSectionPrincipal(){
        return _isSectionPrincipal;
    }

//    @Override
//    public String toString(){
//            StringBuilder name = new StringBuilder(_musician.getUser().getShortcut());
//            name.append(" ".repeat(Math.max(0, 8 - name.length())));
//
//            String wish = "";
//            if (_musician.getNegativeDutyWish(_duty).isPresent()) {
//                wish = "-W";
//            } else if (_musician.getNegativeDateWish(_duty).isPresent()) {
//                wish = "-W";
//            } else if (_musician.getPositiveWish(_duty).isPresent()) {
//                wish = "+W";
//            }
//
//
//            StringBuilder pointsString = new StringBuilder();
//            pointsString.append(_musician.getPointsOfMonth(_duty.getStart().toLocalDate()));
//            pointsString.append('/');
//            pointsString.append(_musician.getRequiredPointsOfMonth(_duty.getStart().toLocalDate()));
//
//            pointsString.append(" ".repeat(Math.max(0, 9 - pointsString.length())));
//
//            String sectionPrincipalString = "      ";
//            if (_musician.isSectionPrincipal()) {
//                sectionPrincipalString = "  SP  ";
//            }
//
//            _displayedString = name  + sectionPrincipalString + "  " +  pointsString +"   "+  wish;
//        return _displayedString;
//    }
}
