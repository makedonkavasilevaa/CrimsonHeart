package mk.finki.ukim.mk.crimsonheart;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.web.InstitutionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class InstitutionServiceTest {
    @Mock
    private InstitutionService institutionService;

    @Mock
    private LocationService locationService;

    @Mock
    private InstitutionRepository institutionRepository;

    private InstitutionController institutionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        institutionController = new InstitutionController(locationService, institutionService);
    }

    @Test
    void testCreateInstitutionWithIncompleteFields() {
        // Simulate a call to create institution with incomplete fields
        String name = "";
        String phone = "123456789";
        String email = "test@test.com";
        InstitutionsType type = InstitutionsType.BLOOD_BANK;
        Long locationId = 1L;  // Assume this exists in the location service

        // Mock the creation and check for expected behavior
        doThrow(new IllegalArgumentException("Name cannot be empty")).when(institutionService)
                .create(name, phone, email, type, locationId);

        // Test for expected exception
        assertThrows(IllegalArgumentException.class, () ->
                institutionController.saveInstitution(null, name, email, phone, type, locationId));
    }

    @Test
    void testCreateInstitutionWithDuplicateValues() {
        // Simulate a call with a duplicate institution name
        String name = "Test Institution";
        String phone = "987654321";
        String email = "duplicate@test.com";
        InstitutionsType type = InstitutionsType.RED_CROSS;
        Long locationId = 2L;  // Assume this exists in the location service

        // Mock the behavior of the repository for duplicate checks
        when(institutionRepository.findByNameContainingIgnoreCase(name)).thenReturn(Optional.of(new Institution()));

        // Ensure that the institution service does not allow duplicates
        assertThrows(IllegalStateException.class, () ->
                institutionController.saveInstitution(null, name, email, phone, type, locationId));
    }

    @Test
    void testCreateInstitutionWithValidFields() {
        // Simulate a valid institution creation
        String name = "Valid Institution";
        String phone = "123123123";
        String email = "valid@test.com";
        InstitutionsType type = InstitutionsType.HOSPITAL;
        Long locationId = 1L;

        // Simulate the saving of an institution
        doNothing().when(institutionService).create(name, phone, email, type, locationId);

        // Simulate the controller call
        institutionController.saveInstitution(null, name, email, phone, type, locationId);

        // Verify the service method was called
        verify(institutionService, times(1)).create(name, phone, email, type, locationId);
    }
}
