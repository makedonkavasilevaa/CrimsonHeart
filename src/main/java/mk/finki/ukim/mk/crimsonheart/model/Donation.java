package mk.finki.ukim.mk.crimsonheart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "donation_event")
public class Donation {

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
    @JoinColumn(name = "organised_by")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "managed_by")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private Location location;

    @Temporal(TemporalType.DATE)
    private Date dateAndTime;

    @OneToMany(mappedBy = "donation")
    private List<Exam> examList;

    public Donation(String name, String description, DonationType donationType, Location location, Date dateAndTime, Institution institution, Users user) {
        this.name = name;
        this.description = description;
        this.donationType = donationType;
        this.location = location;
        this.dateAndTime = dateAndTime;
        this.institution = institution;
        this.user = user;
    }
}
