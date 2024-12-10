package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @DateTimeFormat
    Date performedOn;

    @ManyToOne
    @JoinColumn(name = "donated")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private Users doctor;

    @ManyToOne
    @JoinColumn(name = "patient")
    private Users patient;

    @ManyToOne
    @JoinColumn(name = "nurse")
    private Users nurse;

    boolean successfulExam;

    public Exam(Date performedOn, Donation donation, Users doctor, Users patient, Users nurse, boolean successfulExam) {
        this.performedOn = performedOn;
        this.donation = donation;
        this.doctor = doctor;
        this.patient = patient;
        this.nurse = nurse;
        this.successfulExam = successfulExam;
    }
}
