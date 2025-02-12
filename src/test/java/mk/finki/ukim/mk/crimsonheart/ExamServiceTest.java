package mk.finki.ukim.mk.crimsonheart;import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExamServiceTest {

    @Mock
    private UsersService usersService;

    @Mock
    private DonationEventService donationEventService;

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private ExamService examService;

    private Users doctor;
    private Users patient;
    private Users nurse;
    private DonationEvent donationEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Create test data
        doctor = new Users("Dr. Test", "doctor@example.com", Roles.DOCTOR);
        patient = new Users("John Doe", "john.doe@example.com", Roles.PATIENT);
        nurse = new Users("Nurse Test", "nurse@example.com", Roles.NURSE);
        donationEvent = new DonationEvent(new Date(), "Blood Drive", "Location A");

        // Mock behavior of save() method based on its return type
//        when(usersService.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the passed user

//        when(donationEventService.save(any(DonationEvent.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the passed event

        // Mock findAll()
        when(examRepository.findAll()).thenReturn(List.of());

        // Call save() to ensure mocks are being used
//        usersService.save(doctor);
//        usersService.save(patient);
//        usersService.save(nurse);
//        donationEventService.save(donationEvent);
    }
}
