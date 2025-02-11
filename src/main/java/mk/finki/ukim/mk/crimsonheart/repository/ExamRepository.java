package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Optional<Exam> findById(Long id);
    void deleteById(Long id);
    List<Exam> findAllByPatientNameContainsOrPatientSurnameContains(String patientName, String patientSurname);
    List<Exam> findAllByDoctorName(String doctorName);
    List<Exam> findAllByNurseName(String nurseName);
    List<Exam> findAllByDoctorNameContainsOrNurseNameContainsOrPatientNameContains(String doctorName, String nurseName, String patientName);
    List<Exam> findAllByDoctorSurnameContainsOrNurseSurnameContainsOrPatientSurnameContains(String doctorName, String nurseName, String patientName);
    List<Exam> findAllByDonationEvent(DonationEvent donationEvent);
    List<Exam> findAllByPatientEmbg(String patientEmbg);
    List<Exam> findAllByDonationEventAndPatientNameContainsOrPatientSurnameContains(DonationEvent donationEvent, String patientName, String patientSurname);
    List<Exam> findAllByPatientNameContainsOrPatientSurnameContainsAndPatientEmbg(String patientName,String patientSurname, String patientEmbg);
    List<Exam> findAllByPatientEmbgAndDonationEvent(String patientEmbg, DonationEvent donationEvent);
    List<Exam> findAllByPatientEmbgAndDonationEventAndPatientNameContainsOrPatientSurnameContains(String patientEmbg, DonationEvent donationEvent, String patientName, String patientSurname);
}
