package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.EmploymentStatus;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;

import jakarta.persistence.*;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@ToString(exclude = {"doctorExams", "patientExams", "nurseExams", "location", "worksAt"}) // Prevent recursion in toString()
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(value = EnumType.STRING)
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

    @Column(unique = true)
    String email;

    String password;

    String phone;

    @Column(length = 13, unique = true)
    @NonNull
    String embg;

    @ManyToOne
    private Location location;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

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

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public Users(Roles role, String name, String surname,Date birthday, Sex sex, String email, String phone, String embg, Location location, BloodType bloodType, boolean isDonor, Date lastDonation, EmploymentStatus employmentStatus) {
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
        this.employmentStatus = employmentStatus;
    }

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

    public Users(Roles role, @NonNull String name, @NonNull String surname, @NonNull Date birthday, Sex sex, String email, String password, String phone, @NonNull String embg) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.embg = embg;
    }

    public Users(String s, String mail, Roles roles) {
    }

    public String getNameForShow() {
        return name + " " + surname + " - " + role.name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
