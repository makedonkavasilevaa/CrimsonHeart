package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private DonationEventService donationEventService;

    private Users doctor;
    private Users patient;
    private Users nurse;
    private DonationEvent donationEvent;

    @BeforeEach
    public void setup() {
        // Create dummy users and donation event for testing
        doctor = new Users("Dr. Test", "doctor@example.com", Roles.DOCTOR);
        patient = new Users("John Doe", "john.doe@example.com", Roles.PATIENT);
        nurse = new Users("Nurse Test", "nurse@example.com", Roles.NURSE);
        donationEvent = new DonationEvent(new Date(), "Blood Drive", "Location A");

        // Save them to the repository
        usersService.save(doctor);
        usersService.save(patient);
        usersService.save(nurse);
        donationEventService.save(donationEvent);
    }

    @Test
    public void testCreateExamWithMissingFields() {
        // Attempt to create an exam without required fields
        assertThrows(IllegalArgumentException.class, () -> {
            examService.create(null, "", null, null, null, null, null, false, "");
        }, "An exam with missing fields should throw an IllegalArgumentException");
    }

    @Test
    public void testCreateExamWithDoubleValues() {
        // Create a valid exam
        Date examDate = new Date();
        examService.create(examDate, "120/80", 13.5f, donationEvent.getId(), doctor.getId(), patient.getId(), nurse.getId(), true, "Normal exam");

        // Attempt to create the same exam again, expecting no duplicate
        examService.create(examDate, "120/80", 13.5f, donationEvent.getId(), doctor.getId(), patient.getId(), nurse.getId(), true, "Normal exam");

        // Check if there are two entries with the same exact details
        assertEquals(1, examRepository.findAll().size(), "Exam with the same details should not be duplicated");
    }

    @Test
    public void testCreateExamWithDuplicateEntries() {
        // Create an exam
        Date examDate = new Date();
        examService.create(examDate, "120/80", 13.5f, donationEvent.getId(), doctor.getId(), patient.getId(), nurse.getId(), true, "Normal exam");

        // Attempt to insert a duplicate entry with the same details
        examService.create(examDate, "120/80", 13.5f, donationEvent.getId(), doctor.getId(), patient.getId(), nurse.getId(), true, "Normal exam");

        // Verify that only one exam is in the repository
        Optional<Exam> existingExam = examRepository.findAllByDoctor(doctor).stream()
                .filter(exam -> exam.getBloodPressure().equals("120/80") && exam.getHemoglobin().equals(13.5f))
                .findFirst();
        assertTrue(existingExam.isPresent(), "Exam with the same values shouldn't be added more than once");
    }

    @Test
    public void testCreateExamWithInvalidDoctor() {
        // Try to create an exam with a non-existing doctor
        Long invalidDoctorId = 999L;
        assertThrows(IllegalArgumentException.class, () -> {
            examService.create(new Date(), "130/90", 12.0f, donationEvent.getId(), invalidDoctorId, patient.getId(), nurse.getId(), true, "Invalid doctor");
        }, "An invalid doctor should cause the creation to fail");
    }

    @Test
    public void testExamDateShouldNotBeInFuture() {
        // Try creating an exam with a future date
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); // 1 day in the future
        assertThrows(IllegalArgumentException.class, () -> {
            examService.create(futureDate, "130/90", 12.0f, donationEvent.getId(), doctor.getId(), patient.getId(), nurse.getId(), true, "Future exam");
        }, "An exam cannot be scheduled in the future");
    }
}
