package at.fhv.teamb.symphoniacus.persistence.model;

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
public class MusicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianId")
    private Integer musicianId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId")
    private SectionEntity section;

    @OneToMany(mappedBy = "musician")
    private List<ContractualObligationEntity> contractualObligations = new LinkedList<>();

    @ManyToMany
    @JoinTable(
        name = "musicianRole_musician",
        joinColumns = {
            @JoinColumn(name = "musicianId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "musicianRoleId")
        }
    )
    private List<MusicianRole> musicianRoles = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    private List<DutyPositionEntity> dutyPositions = new LinkedList<>();

    @OneToMany(mappedBy = "musician")
    private List<PositiveWishEntity> positiveWishes = new LinkedList<>();

    @OneToMany(mappedBy = "musician")
    private List<NegativeDutyWishEntity> negativeDutyWishes = new LinkedList<>();

    @OneToMany(mappedBy = "musician")
    private List<VacationEntity> vacations = new LinkedList<>();

    @OneToMany(mappedBy = "musician")
    private List<NegativeDateWishEntity> negativeDateWishes = new LinkedList<>();

    public void addPosition(DutyPositionEntity position) {
        this.dutyPositions.add(position);
        position.setMusician(this);
    }

    public void removePosition(DutyPositionEntity position) {
        this.dutyPositions.remove(position);
        position.setMusician(null);
    }

    public void addPositiveWish(PositiveWishEntity positiveWish) {
        this.positiveWishes.add(positiveWish);
        positiveWish.setMusician(this);
    }

    public void removePositiveWish(PositiveWishEntity positiveWish) {
        this.positiveWishes.remove(positiveWish);
        positiveWish.setMusician(null);
    }

    public void addNegativeDutyWish(NegativeDutyWishEntity negativeWish) {
        this.negativeDutyWishes.add(negativeWish);
        negativeWish.setMusician(this);
    }
    public void removeNegativeDutyWish(NegativeDutyWishEntity negativeWish) {
        this.negativeDutyWishes.remove(negativeWish);
        negativeWish.setMusician(null);
    }

    public void addNegativeDateWish(NegativeDateWishEntity negativeWish) {
        this.negativeDateWishes.add(negativeWish);
        negativeWish.setMusician(this);
    }

    public void removeNegativeDateWish(NegativeDateWishEntity negativeWish) {
        this.negativeDateWishes.remove(negativeWish);
        negativeWish.setMusician(null);
    }

    public void setContractualObligations(
        List<ContractualObligationEntity> contractualObligations) {
        this.contractualObligations = contractualObligations;
    }

    public List<NegativeDutyWishEntity> getNegativeDutyWishes() {
        return negativeDutyWishes;
    }

    public void setNegativeDutyWishes(
        List<NegativeDutyWishEntity> negativeDutyWishes) {
        this.negativeDutyWishes = negativeDutyWishes;
    }

    public void setMusicianRoles(
        List<MusicianRole> musicianRoles) {
        this.musicianRoles = musicianRoles;
    }

    public void setDutyPositions(
        List<DutyPositionEntity> dutyPositions) {
        this.dutyPositions = dutyPositions;
    }

    public List<PositiveWishEntity> getPositiveWishes() {
        return positiveWishes;
    }

    public void setPositiveWishes(
        List<PositiveWishEntity> positiveWishes) {
        this.positiveWishes = positiveWishes;
    }


    public void setVacations(
        List<VacationEntity> vacations) {
        this.vacations = vacations;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public List<ContractualObligationEntity> getContractualObligations() {
        return this.contractualObligations;
    }

    public void addContractualObligation(ContractualObligationEntity contractualObligation) {
        this.contractualObligations.add(contractualObligation);
        contractualObligation.setMusician(this);
    }

    public void removeContractualObligation(ContractualObligationEntity contractualObligation) {
        this.contractualObligations.remove(contractualObligation);
        contractualObligation.setMusician(null);
    }

    public List<MusicianRole> getMusicianRoles() {
        return this.musicianRoles;
    }

    public void addMusicianRole(MusicianRole role) {
        this.musicianRoles.add(role);
        role.addMusician(this);
    }

    public void removeMusicianRole(MusicianRole role) {
        this.musicianRoles.remove(role);
        role.removeMusician(this);
    }

    public List<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void addDutyPosition(DutyPositionEntity position) {
        this.dutyPositions.add(position);
        position.setMusician(this);
    }

    public void removeDutyPosition(DutyPositionEntity position) {
        this.dutyPositions.remove(position);
        position.setMusician(null);
    }

    public List<VacationEntity> getVacations() {
        return this.vacations;
    }

    public void addVacation(VacationEntity vacation) {
        this.vacations.add(vacation);
        vacation.setMusician(this);
    }

    public void removeVacation(VacationEntity vacation) {
        this.vacations.remove(vacation);
        vacation.setMusician(null);
    }
}
