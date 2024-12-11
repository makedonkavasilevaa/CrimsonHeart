package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.NonNull;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Temporal(TemporalType.DATE)
    Date performedOn;

    @NonNull
    String bloodPressure;

    @NonNull
    Float hemoglobin;

    @ManyToOne
    @JoinColumn(name = "donated")
    private DonationEvent donationEvent;

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

    public Exam(Date performedOn, String bloodPressure, Float hemoglobin, DonationEvent donationEvent, Users doctor, Users patient, Users nurse, boolean successfulExam) {
        this.performedOn = performedOn;
        this.bloodPressure = bloodPressure;
        this.hemoglobin = hemoglobin;
        this.donationEvent = donationEvent;
        this.doctor = doctor;
        this.patient = patient;
        this.nurse = nurse;
        this.successfulExam = successfulExam;
    }
}
