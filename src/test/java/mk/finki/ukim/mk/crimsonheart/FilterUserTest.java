package mk.finki.ukim.mk.crimsonheart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.enums.EmploymentStatus;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import util.SubmissionHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FilterUserTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users user1;
    private Users user2;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Ensure that Mockito initializes the mocks

        // Set up test data
        user1 = new Users(Roles.PATIENT, "Alice", "Johnson", (Date) null, (Sex) null, "alice@example.com", "123456789", "1234567890123", (Location) null, BloodType.Oneg, false, (Date) null, (EmploymentStatus) null);
        user1.setId(1L);
        user2 = new Users(Roles.DOCTOR, "Bob", "White", (Date) null, (Sex) null, "bob@example.com", "987654321", "9876543210123", (Location) null, BloodType.Apos, true, (Date) null, (EmploymentStatus) null);
        user2.setId(2L);
    }

    @Test
    public void test_filter_users_by_any_role() {
        // Start the test
        SubmissionHelper.startTest("test-filter-users-by-any-role");

        // Filter by any role (Patients)
        when(usersRepository.getUsersByFilter(Roles.PATIENT, "", "", null)).thenReturn(Arrays.asList(user1));
        List<Users> users = usersService.filterUsers("", "", Roles.PATIENT, null);
        assertEquals(1, users.size(), "Expected 1 user with role PATIENT");
        assertEquals("Alice", users.get(0).getName(), "Expected user name to be 'Alice'");

        // Filter by any role (Doctors)
        when(usersRepository.getUsersByFilter(Roles.DOCTOR, "", "", null)).thenReturn(Arrays.asList(user2));
        users = usersService.filterUsers("", "", Roles.DOCTOR, null);
        assertEquals(1, users.size(), "Expected 1 user with role DOCTOR");
        assertEquals("Bob", users.get(0).getName(), "Expected user name to be 'Bob'");

        // End the test
        SubmissionHelper.endTest();
    }
}
