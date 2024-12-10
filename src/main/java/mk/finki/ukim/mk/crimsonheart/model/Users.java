package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "doctor")
    private List<Exam> doctorExams;

    @OneToMany(mappedBy = "patient")
    private List<Exam> patientExams;

    @OneToMany(mappedBy = "nurse")
    private List<Exam> nurseExams;

    boolean hasBeenRejected;

    Integer timesRejected = 0;

//    String password;
}
