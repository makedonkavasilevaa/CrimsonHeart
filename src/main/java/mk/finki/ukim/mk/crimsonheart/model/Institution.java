package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    private InstitutionsType institutionsType;

    @OneToOne
    private Location location;

    @OneToMany(mappedBy = "institution")
    private List<Donation> donationEvents;
}
