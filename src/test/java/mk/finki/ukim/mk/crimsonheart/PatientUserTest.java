package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.EmploymentStatus;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientUserTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = new Users(Roles.PATIENT, "John", "Doe", new Date(), Sex.MALE, "john@example.com", "1234567890", "1234567890123", null, BloodType.Opos, false, null, EmploymentStatus.EMPLOYED);
    }

    @Test
    void testAddPatientWithMissingFields() {
        Users incompleteUser = new Users((Roles) null, "", "", (Date) null, (Sex) null, "", "", "", (Location) null, (BloodType) null, false, (Date) null, (EmploymentStatus) null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usersService.createPatient(
                    incompleteUser.getRole(),
                    incompleteUser.getName(),
                    incompleteUser.getSurname(),
                    incompleteUser.getBirthday(),
                    incompleteUser.getSex(),
                    incompleteUser.getEmail(),
                    incompleteUser.getPhone(),
                    incompleteUser.getEmbg(),
                    null,
                    incompleteUser.getBloodType(),
                    incompleteUser.isDonor(),
                    incompleteUser.getLastDonation(),
                    incompleteUser.getEmploymentStatus()
            );
        });
        assertEquals("EMBG isn't in the correct format", exception.getMessage());
    }

    @Test
    void testAddPatientWithDuplicateValues() {
        when(usersRepository.findAllByEmbg(testUser.getEmbg())).thenReturn(testUser);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usersService.createPatient(
                    testUser.getRole(),
                    testUser.getName(),
                    testUser.getSurname(),
                    testUser.getBirthday(),
                    testUser.getSex(),
                    testUser.getEmail(),
                    testUser.getPhone(),
                    testUser.getEmbg(),
                    null,
                    testUser.getBloodType(),
                    testUser.isDonor(),
                    testUser.getLastDonation(),
                    testUser.getEmploymentStatus()
            );
        });
        assertEquals("User with this EMBG already exists", exception.getMessage());
    }

    @Test
    void testAddPatientWithExistingEmail() {
        when(usersRepository.findAllByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usersService.createPatient(
                    testUser.getRole(),
                    testUser.getName(),
                    testUser.getSurname(),
                    testUser.getBirthday(),
                    testUser.getSex(),
                    testUser.getEmail(),
                    testUser.getPhone(),
                    testUser.getEmbg(),
                    null,
                    testUser.getBloodType(),
                    testUser.isDonor(),
                    testUser.getLastDonation(),
                    testUser.getEmploymentStatus()
            );
        });
        assertEquals("Email already in use", exception.getMessage());
    }

}
