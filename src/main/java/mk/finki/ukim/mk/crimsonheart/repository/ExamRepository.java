package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Optional<Exam> findById(Long id);
    void deleteById(Long id);
    Optional<Exam> findAllByPatientName(String patientName);
    Optional<Exam> findAllByPatientEmbg(String patientEmbg);
    Optional<Exam> findAllByDoctorName(String doctorName);
    Optional<Exam> findAllByNurseName(String nurseName);
    Optional<Exam> findAllByDoctorNameOrNurseNameOrPatientName(String doctorName, String nurseName, String patientName);
}
