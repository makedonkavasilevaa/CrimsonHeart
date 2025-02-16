package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository; // Mock the repository

    @InjectMocks
    private UsersService usersService; // Inject the mock repository into the service

    @BeforeEach
    void setUp() {
        // No need for manual instantiation, @InjectMocks handles it
    }

    @Test
    void testFilterUsersByName() {
        // Given
        String name = "John";
        List<Users> filteredUsers = Arrays.asList(
                new Users("Patient", "John", "Doe", null, null, "john@example.com", "123456789", null, new Location(), BloodType.A_POSITIVE, false, null, null)
        );

        // When
        when(usersRepository.findByNameContainingIgnoreCase(name)).thenReturn(filteredUsers);

        // Call service method to filter users by name
        List<Users> result = usersService.filterUsers(name, null, null, null);

        // Then
        verify(usersRepository).findByNameContainingIgnoreCase(name); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of users
        assertEquals("John", result.get(0).getFirstName()); // Verify correct user was returned
    }

    @Test
    void testFilterUsersByBloodType() {
        // Given
        BloodType bloodType = BloodType.A_POSITIVE;
        List<Users> filteredUsers = Arrays.asList(
                new Users("Patient", "Jane", "Doe", null, null, "jane@example.com", "987654321", null, new Location(), bloodType, false, null, null)
        );

        // When
        when(usersRepository.findByBloodType(bloodType)).thenReturn(filteredUsers);

        // Call service method to filter users by blood type
        List<Users> result = usersService.filterUsers(null, null, null, bloodType);

        // Then
        verify(usersRepository).findByBloodType(bloodType); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of users
        assertEquals(BloodType.A_POSITIVE, result.get(0).getBloodType()); // Verify correct blood type
    }

    @Test
    void testFilterUsersByMultipleCriteria() {
        // Given
        String name = "John";
        BloodType bloodType = BloodType.A_NEGATIVE;
        List<Users> filteredUsers = Arrays.asList(
                new Users("Patient", "John", "Doe", null, null, "john@example.com", "123456789", null, new Location(), bloodType, false, null, null)
        );

        // When
        when(usersRepository.findByNameContainingIgnoreCaseAndBloodType(name, bloodType)).thenReturn(filteredUsers);

        // Call service method to filter users by both name and blood type
        List<Users> result = usersService.filterUsers(name, null, null, bloodType);

        // Then
        verify(usersRepository).findByNameContainingIgnoreCaseAndBloodType(name, bloodType); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of users
        assertEquals("John", result.get(0).getFirstName()); // Verify correct user was returned
        assertEquals(BloodType.A_NEGATIVE, result.get(0).getBloodType()); // Verify correct blood type
    }

    @Test
    void testFilterUsers_NoResults() {
        // Given
        String name = "NonexistentName";
        List<Users> filteredUsers = Arrays.asList();

        // When
        when(usersRepository.findByNameContainingIgnoreCase(name)).thenReturn(filteredUsers);

        // Call service method to filter users by name
        List<Users> result = usersService.filterUsers(name, null, null, null);

        // Then
        assertTrue(result.isEmpty()); // Ensure no users are returned
    }
}
