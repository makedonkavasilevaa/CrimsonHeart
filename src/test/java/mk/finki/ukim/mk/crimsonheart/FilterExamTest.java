package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private ExamService examService;

    private Users doctor;
    private Users patient;
    private Users nurse;
    private DonationEvent donationEvent;
    private Exam exam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Create test data
        doctor = new Users("Dr. Test", "doctor@example.com", Roles.DOCTOR);
        patient = new Users("John Doe", "john.doe@example.com", Roles.PATIENT);
        nurse = new Users("Nurse Test", "nurse@example.com", Roles.NURSE);
        donationEvent = new DonationEvent(new Date(), "Blood Drive", "Location A");

        exam = new Exam(new Date(), "120/80", 13.5F, donationEvent, doctor, patient, nurse, true, "Exam successful");
    }

    @Test
    public void testFindByName() {
        // Given
        when(examRepository.findByName("John")).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.findByName("John");

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("John Doe", exams.get(0).getPatient().getUsername());
    }

    @Test
    public void testFindByPatientEmbg() {
        // Given
        String embg = "1234567890123";  // Mock EMBG for test
        when(examRepository.findByPatientEmbg(embg)).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.findByPatientEmbg(embg);

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("John Doe", exams.get(0).getPatient().getUsername());
    }

    @Test
    public void testFindByEvent() {
        // Given
        Long eventId = donationEvent.getId();
        when(examRepository.findByDonationEventId(eventId)).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.findByEvent(eventId);

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("Blood Drive", exams.get(0).getDonationEvent().getName());
    }

    @Test
    public void testFindByNameAndEvent() {
        // Given
        Long eventId = donationEvent.getId();
        when(examRepository.findByNameAndDonationEventId("John", eventId)).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.findByNameAndEvent(eventId, "John");

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("John Doe", exams.get(0).getPatient().getUsername());
        assertEquals("Blood Drive", exams.get(0).getDonationEvent().getName());
    }

    @Test
    public void testFindByNameAndPatientEmbgAndEvent() {
        // Given
        Long eventId = donationEvent.getId();
        String embg = "1234567890123";
        when(examRepository.findByNameAndPatientEmbgAndEvent("John", embg, eventId)).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.findByNameAndPatientEmbgAndEvent("John", embg, eventId);

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("John Doe", exams.get(0).getPatient().getUsername());
        assertEquals("Blood Drive", exams.get(0).getDonationEvent().getName());
    }

    @Test
    public void testFindAllExams() {
        // Given
        when(examRepository.findAll()).thenReturn(Arrays.asList(exam));

        // When
        List<Exam> exams = examService.listAll();

        // Then
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals("John Doe", exams.get(0).getPatient().getUsername());
    }
}
