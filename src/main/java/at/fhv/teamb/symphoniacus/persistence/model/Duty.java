package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name="duty")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int dutyId;
}
