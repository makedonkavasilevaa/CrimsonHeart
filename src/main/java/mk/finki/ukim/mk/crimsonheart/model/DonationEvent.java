package mk.finki.ukim.mk.crimsonheart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "donation_event")
public class DonationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(length = 300)
    String description;

    @Enumerated(EnumType.STRING)
    DonationType donationType;

    @ManyToOne
    @JoinColumn(name = "organsied_by")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "managed_by")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private Location location;

    @Temporal(TemporalType.DATE)
    private Date dateOfEvent;

    String timeOfEvent;

    @OneToMany(mappedBy = "donationEvent")
    private List<Exam> examList;

    public DonationEvent(String name, String description, DonationType donationType, Location location, Date dateOfEvent, String timeOfEvent, Institution institution, Users user) {
        this.name = name;
        this.description = description;
        this.donationType = donationType;
        this.location = location;
        this.dateOfEvent = dateOfEvent;
        this.timeOfEvent = timeOfEvent;
        this.institution = institution;
        this.user = user;
    }
}
