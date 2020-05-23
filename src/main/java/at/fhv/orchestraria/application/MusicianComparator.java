package at.fhv.orchestraria.application;

import at.fhv.orchestraria.domain.Imodel.IDuty;
import at.fhv.orchestraria.domain.Imodel.IMusician;

import java.util.Comparator;

/**
 * MusicianComparator to compare two IMusicians and rank them for a IDuty.
 */
public class MusicianComparator implements Comparator<IMusician> {
    private IDuty _duty;

    public MusicianComparator(IDuty duty){
        _duty = duty;
    }


    /**
     *  Compares two musicians. Musicians with wishes concerning the duty in any way have bigger priority
     *  than people without wishes.
     *  People without wishes are being sorted by the already achieved points in the month of the duty descending.
     * @param o1 Musician 1
     * @param o2 Musician 2
     * @return 0 for equals, 1 for o1 > o2 and -1 for o1 < o2.
     */
    @Override
    public int compare(IMusician o1, IMusician o2) {
        boolean hasWish1 = hasAWish(o1);
        boolean hasWish2 = hasAWish(o2);
        if(hasWish1 && hasWish2){
            return 0;
        }
        if(hasWish1){
            return -1;
        }
        if(hasWish2){
            return 1;
        }
        if(o1.getPointsOfMonth(_duty.getStart().toLocalDate()) < o2.getPointsOfMonth(_duty.getStart().toLocalDate())){
            return -1;
        }else if(o1.getPointsOfMonth(_duty.getStart().toLocalDate()) > o2.getPointsOfMonth(_duty.getStart().toLocalDate())){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * I used to check if a musician has wishes for a duty
     * @param m is a musician
     * @return a boolean witch says if the musician has a wish
     */
    private boolean hasAWish(IMusician m){
        return m.getPositiveWish(_duty).isPresent() || m.getNegativeDateWish(_duty).isPresent() || m.getNegativeDutyWish(_duty).isPresent();
    }
}
