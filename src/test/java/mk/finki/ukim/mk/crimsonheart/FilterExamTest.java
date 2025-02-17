package mk.finki.ukim.mk.crimsonheart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.service.impl.ExamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.List;

public class FilterExamTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private DonationEventRepository eventRepository;

    @InjectMocks
    private ExamServiceImpl examService;

    private Users doctor;
    private Users patient;
    private Users nurse;
    private DonationEvent donationEvent;
    private Exam exam1;
    private Exam exam2;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Set up test data
        doctor = new Users();
        doctor.setName("Dr. Smith");
        doctor.setId(1L);

        nurse = new Users();
        nurse.setName("Nurse Jane");
        nurse.setId(2L);

        patient = new Users();
        patient.setName("John Doe");
        patient.setId(3L);
        patient.setEmbg("1234567890123");

        donationEvent = new DonationEvent(); // Assuming DonationEvent is already set up
        donationEvent.setId(1L);

        exam1 = new Exam(new Date(), "120/80", 13.5f, donationEvent, doctor, patient, nurse, true, "No issues");
        exam1.setId(1L);

        exam2 = new Exam(new Date(), "130/90", 14.0f, donationEvent, doctor, patient, nurse, false, "Blood pressure high");
        exam2.setId(2L);
    }

    @Test
    public void test_findByNameAndPatientEmbgAndEvent() {
        // Start the test
        System.out.println("Starting test_findByNameAndPatientEmbgAndEvent");

        // Mock the repository behavior
        when(eventRepository.findById(1L)).thenReturn(java.util.Optional.of(donationEvent));  // Mock event retrieval
        when(examRepository.filterExams("Dr. Smith", "1234567890123", donationEvent))
                .thenReturn(List.of(exam1, exam2));

        // Call the service method
        List<Exam> exams = examService.findByNameAndPatientEmbgAndEvent("Dr. Smith", "1234567890123", 1L);

        // Assertions
        assertEquals(2, exams.size(), "Expected 2 exams for the specified doctor, patient, and event");
        assertEquals("Dr. Smith", exams.get(0).getDoctor().getName(), "Expected doctor name to be 'Dr. Smith'");
        assertEquals("John Doe", exams.get(0).getPatient().getName(), "Expected patient name to be 'John Doe'");
        assertEquals("120/80", exams.get(0).getBloodPressure(), "Expected blood pressure to be '120/80'");

        // End the test
        System.out.println("Ending test_findByNameAndPatientEmbgAndEvent");
    }
}
