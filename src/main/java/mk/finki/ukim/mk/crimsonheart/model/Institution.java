package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;

@Entity
@Data
@NoArgsConstructor
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String phone;
    String email;

    @Enumerated(EnumType.STRING)
    private InstitutionsType institutionsType;

    @OneToOne
    private Location location;
}
