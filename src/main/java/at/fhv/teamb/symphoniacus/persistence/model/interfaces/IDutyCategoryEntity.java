package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.List;

public interface IDutyCategoryEntity {
    Integer getDutyCategoryId();

    void setDutyCategoryId(Integer dutyCategoryId);

    String getType();

    void setType(String type);

    boolean getIsRehearsal();

    void setIsRehearsal(boolean isRehearsal);

    Integer getPoints();

    void setPoints(Integer points);

    List<IDutyCategoryChangelogEntity> getDutyCategoryChangelogs();

    void addDutyCategoryChangelog(IDutyCategoryChangelogEntity dutyCategoryChangelog);

    void removeDutyCategoryChangelog(IDutyCategoryChangelogEntity dutyCategoryChangelog);

    List<DutyEntity> getDuties();

    void addDuty(DutyEntity duty);

    void removeDuty(DutyEntity duty);
}
