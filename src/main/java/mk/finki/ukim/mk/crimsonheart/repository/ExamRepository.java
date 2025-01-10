package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Optional<Exam> findById(Long id);
    void deleteById(Long id);
    List<Exam> findAllByPatientName(String patientName);
    List<Exam> findAllByPatientEmbg(String patientEmbg);
    List<Exam> findAllByDoctorName(String doctorName);
    List<Exam> findAllByNurseName(String nurseName);
    List<Exam> findAllByDoctorNameOrNurseNameOrPatientName(String doctorName, String nurseName, String patientName);
}
