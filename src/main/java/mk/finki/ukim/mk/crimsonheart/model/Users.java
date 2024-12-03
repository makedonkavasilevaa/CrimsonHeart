package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    String name;
    String surname;
    String email;
    String phone;
    String address;

    @Column(length = 13)
    String embg;

    @ManyToOne
    private Location location;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

//    String password;
}
