package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.PositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.LinkedList;
import java.util.List;

public class WishRequestManager {

    private final PositiveWishDao positiveWishDao;
    private final NegativeDutyWishDao negDutyWishDao;
    private final NegativeDateWishDao negDateWishDao;
    private List<WishRequestable> allWishRequests;

    public WishRequestManager() {
        this.positiveWishDao = new PositiveWishDao();
        this.negDutyWishDao = new NegativeDutyWishDao();
        this.negDateWishDao = new NegativeDateWishDao();
        allWishRequests = new LinkedList<>();
    }

    public void loadAllWishRequests(DutyEntity duty) {
        this.allWishRequests.addAll(positiveWishDao.getAllPositiveWishes(duty));
        /*
        this.allWishRequests.addAll(negDateWishDao.getAllNegativeDateWishes(duty));
        this.allWishRequests.addAll(negDateWishDao.getAllNegativeDateWishes(duty));
         */

    }



}
