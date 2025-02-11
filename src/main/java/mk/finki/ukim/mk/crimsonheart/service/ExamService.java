package mk.finki.ukim.mk.crimsonheart.service;

import lombok.NonNull;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExamService {

    void create(Date performedOn, String bloodPressure, Float hemoglobin, Long donationEventId, Long doctorId, Long patientId, Long nurseID, boolean successfulExam, String comment);
    void update(Long examId, Date performedOn, String bloodPressure, Float hemoglobin, Long donationEventId, Long doctorId, Long patientId, Long nurseID, boolean successfulExam, String comment);
    List<Exam> listAll();
    Exam findById(Long id);
    void deleteById(Long id);
    List<Exam> findByName(String name);
    List<Exam> findByEvent(Long eventId);
    List<Exam> findByPatientEmbg (String patientEmbg);
    List<Exam> findByNameAndEvent(Long eventId, String name);
    List<Exam> findByNameAndEmbg(String name, String embg);
    List<Exam> findByPatientEmbgAndEvent(String embg, Long eventId);
    List<Exam> findByNameAndPatientEmbgAndEvent(String name, String embg, Long eventId);

}
