package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;

import jakarta.persistence.*;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@ToString(exclude = {"doctorExams", "patientExams", "nurseExams", "location", "worksAt"}) // Prevent recursion in toString()
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @NonNull
    String name;

    @NonNull
    String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    @NonNull
    Date birthday;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    String email;

    String phone;

    @Column(length = 13)
    @NonNull
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

    boolean hasBeenRejected = false;

    Integer timesRejected = 0;

    boolean isDonor = false;

    @DateTimeFormat
    Date lastDonation;

    @ManyToOne
    @JoinColumn(name = "works_at")
    private Institution worksAt;

    public Users(Roles role, String name, String surname,Date birthday, Sex sex, String email, String phone, String embg, Location location, BloodType bloodType, boolean isDonor, Date lastDonation, Institution worksAt) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.embg = embg;
        this.location = location;
        this.bloodType = bloodType;
        this.isDonor = isDonor;
        this.lastDonation = lastDonation;
        this.worksAt = worksAt;
    }

    public String getNameForShow() {
        return name + " " + surname + " - " + role.name();
    }

}
