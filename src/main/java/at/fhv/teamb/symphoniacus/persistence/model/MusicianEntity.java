package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IVacationEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "musician")
@NamedEntityGraph(
    name = "musician-with-collections",
    attributeNodes = {
        @NamedAttributeNode("user"),
        @NamedAttributeNode("section"),
        @NamedAttributeNode("musicianRoles")
    }
)
public class MusicianEntity implements IMusicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianId")
    private Integer musicianId;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "userId")
    private IUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SectionEntity.class)
    @JoinColumn(name = "sectionId")
    private ISectionEntity section;

    @OneToMany(mappedBy = "musician", targetEntity = ContractualObligationEntity.class)
    private List<IContractualObligationEntity> contractualObligations = new LinkedList<>();

    @ManyToMany(targetEntity = MusicianRole.class)
    @JoinTable(
        name = "musicianRole_musician",
        joinColumns = {
            @JoinColumn(name = "musicianId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "musicianRoleId")
        }
    )
    private List<IMusicianRole> musicianRoles = new LinkedList<>();

    @OneToMany(
        mappedBy = "musician",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = DutyPositionEntity.class
    )
    private List<IDutyPositionEntity> dutyPositions = new LinkedList<>();

    @OneToMany(mappedBy = "musician", targetEntity = PositiveWishEntity.class)
    private List<IPositiveWishEntity> positiveWishes = new LinkedList<>();

    @OneToMany(mappedBy = "musician", targetEntity = NegativeDutyWishEntity.class)
    private List<INegativeDutyWishEntity> negativeDutyWishes = new LinkedList<>();

    @OneToMany(mappedBy = "musician", targetEntity = VacationEntity.class)
    private List<IVacationEntity> vacations = new LinkedList<>();

    @OneToMany(mappedBy = "musician", targetEntity = NegativeDateWishEntity.class)
    private List<INegativeDateWishEntity> negativeDateWishes = new LinkedList<>();

    @Override
    public void addPositiveWish(IPositiveWishEntity positiveWish) {
        this.positiveWishes.add(positiveWish);
        positiveWish.setMusician(this);
    }

    @Override
    public void removePositiveWish(IPositiveWishEntity positiveWish) {
        this.positiveWishes.remove(positiveWish);
        positiveWish.setMusician(null);
    }

    @Override
    public void addNegativeDutyWish(INegativeDutyWishEntity negativeWish) {
        this.negativeDutyWishes.add(negativeWish);
        negativeWish.setMusician(this);
    }

    @Override
    public void removeNegativeDutyWish(INegativeDutyWishEntity negativeWish) {
        this.negativeDutyWishes.remove(negativeWish);
        negativeWish.setMusician(null);
    }

    @Override
    public void addNegativeDateWish(INegativeDateWishEntity negativeWish) {
        this.negativeDateWishes.add(negativeWish);
        negativeWish.setMusician(this);
    }

    @Override
    public void removeNegativeDateWish(INegativeDateWishEntity negativeWish) {
        this.negativeDateWishes.remove(negativeWish);
        negativeWish.setMusician(null);
    }

    @Override
    public void setContractualObligations(
        List<IContractualObligationEntity> contractualObligations
    ) {
        this.contractualObligations = contractualObligations;
    }

    @Override
    public List<INegativeDutyWishEntity> getNegativeDutyWishes() {
        return negativeDutyWishes;
    }

    @Override
    public void setNegativeDutyWishes(List<INegativeDutyWishEntity> negativeDutyWishes) {
        this.negativeDutyWishes = negativeDutyWishes;
    }

    @Override
    public void setMusicianRoles(List<IMusicianRole> musicianRoles) {
        this.musicianRoles = musicianRoles;
    }

    @Override
    public void setDutyPositions(List<IDutyPositionEntity> dutyPositions) {
        this.dutyPositions = dutyPositions;
    }

    @Override
    public List<IPositiveWishEntity> getPositiveWishes() {
        return positiveWishes;
    }

    @Override
    public void setPositiveWishes(List<IPositiveWishEntity> positiveWishes) {
        this.positiveWishes = positiveWishes;
    }


    @Override
    public void setVacations(List<IVacationEntity> vacations) {
        this.vacations = vacations;
    }

    @Override
    public Integer getMusicianId() {
        return this.musicianId;
    }

    @Override
    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    @Override
    public IUserEntity getUser() {
        return this.user;
    }

    @Override
    public void setUser(IUserEntity user) {
        this.user = user;
    }

    @Override
    public ISectionEntity getSection() {
        return section;
    }

    @Override
    public void setSection(ISectionEntity section) {
        this.section = section;
    }

    @Override
    public List<IContractualObligationEntity> getContractualObligations() {
        return this.contractualObligations;
    }

    @Override
    public void addContractualObligation(IContractualObligationEntity contractualObligation) {
        this.contractualObligations.add(contractualObligation);
        contractualObligation.setMusician(this);
    }

    @Override
    public void removeContractualObligation(IContractualObligationEntity contractualObligation) {
        this.contractualObligations.remove(contractualObligation);
        contractualObligation.setMusician(null);
    }

    @Override
    public List<IMusicianRole> getMusicianRoles() {
        return this.musicianRoles;
    }

    @Override
    public void addMusicianRole(IMusicianRole role) {
        this.musicianRoles.add(role);
        role.addMusician(this);
    }

    @Override
    public void removeMusicianRole(IMusicianRole role) {
        this.musicianRoles.remove(role);
        role.removeMusician(this);
    }

    @Override
    public List<IDutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    @Override
    public void addDutyPosition(IDutyPositionEntity position) {
        this.dutyPositions.add(position);
        position.setMusician(this);
    }

    @Override
    public void removeDutyPosition(IDutyPositionEntity position) {
        this.dutyPositions.remove(position);
        position.setMusician(null);
    }

    @Override
    public List<IVacationEntity> getVacations() {
        return this.vacations;
    }

    @Override
    public void addVacation(IVacationEntity vacation) {
        this.vacations.add(vacation);
        vacation.setMusician(this);
    }

    @Override
    public void removeVacation(IVacationEntity vacation) {
        this.vacations.remove(vacation);
        vacation.setMusician(null);
    }
}
