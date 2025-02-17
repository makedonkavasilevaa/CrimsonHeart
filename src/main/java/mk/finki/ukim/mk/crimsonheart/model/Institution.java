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

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<DonationEvent> donationEventEvents;

    @OneToMany(mappedBy = "worksAt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> employees;

    public Institution(String name, String phone, String email, InstitutionsType institutionsType, Location location) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.institutionsType = institutionsType;
        this.location = location;
    }
}
