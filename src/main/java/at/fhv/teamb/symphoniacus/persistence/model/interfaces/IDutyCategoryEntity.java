package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

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

    List<IDutyEntity> getDuties();

    void addDuty(IDutyEntity duty);

    void removeDuty(IDutyEntity duty);
}
