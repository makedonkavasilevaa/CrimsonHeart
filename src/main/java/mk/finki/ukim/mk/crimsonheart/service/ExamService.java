package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Donation;
import mk.finki.ukim.mk.crimsonheart.model.Exam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExamService {

    List<Exam> listAll();
    Optional<Exam> findById(Long id);
    Exam save(Date performedOn, Long donationId, Long doctorId, Long patientId, Long nurseId, boolean successfulExam);
    void deleteById(Long id);
    Optional<Exam> findByName(Roles role, String name);
}
