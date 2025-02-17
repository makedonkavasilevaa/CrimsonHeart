package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>, ExamCustomRepository{

    Optional<Exam> findById(Long id);
    void deleteById(Long id);
    List<Exam> findAllByDoctor(Users doctor);
    List<Exam> findAllByNurse(Users nurse);
    List<Exam> findAllByDonationEvent(DonationEvent donationEvent);
    List<Exam> findAllByPatient(Users patient);
    List<Exam> findAllByDonationEventAndPatientNameContainingIgnoreCase(DonationEvent donationEvent, String patientName);
    List<Exam> findAllByPatientNameContainingIgnoreCaseAndPatient(String patientName, Users patient);
    List<Exam> findAllByPatientAndDonationEvent(Users patient, DonationEvent donationEvent);
    List<Exam> findAllByPatientAndDonationEventAndPatientNameContainingIgnoreCase(Users patient, DonationEvent donationEvent, String patientName);
}
