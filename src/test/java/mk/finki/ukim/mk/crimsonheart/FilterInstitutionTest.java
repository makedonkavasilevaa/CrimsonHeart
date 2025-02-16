package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
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
public class InstitutionServiceTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private InstitutionServiceImpl institutionService; // The service to be tested

    private Location location;
    private Institution institution;

    @BeforeEach
    void setUp() {
        // Set up a sample location and institution for the tests
        location = new Location("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");
        institution = new Institution("Test Institution", "test@example.com", "123456789", InstitutionsType.HOSPITAL, location);
    }

    @Test
    void testFilterInstitutionByName() {
        // Given
        String name = "Test Institution";
        List<Institution> filteredInstitutions = Arrays.asList(institution);

        when(institutionRepository.findByNameContainingIgnoreCase(name)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(name, null, null, null);

        // Then
        verify(institutionRepository).findByNameContainingIgnoreCase(name);
        assertEquals(1, result.size()); // Ensure the correct number of results
        assertEquals("Test Institution", result.get(0).getName()); // Ensure the correct institution is returned
    }

    @Test
    void testFilterInstitutionByType() {
        // Given
        InstitutionsType type = InstitutionsType.HOSPITAL;
        List<Institution> filteredInstitutions = Arrays.asList(institution);

        when(institutionRepository.findByType(type)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(null, type, null, null);

        // Then
        verify(institutionRepository).findByType(type);
        assertEquals(1, result.size());
        assertEquals(InstitutionsType.HOSPITAL, result.get(0).getType());
    }

    @Test
    void testFilterInstitutionByCity() {
        // Given
        CityEnum city = CityEnum.SKOPJE;
        List<Institution> filteredInstitutions = Arrays.asList(institution);

        when(institutionRepository.findByLocationCity(city)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(null, null, null, city);

        // Then
        verify(institutionRepository).findByLocationCity(city);
        assertEquals(1, result.size());
        assertEquals(CityEnum.SKOPJE, result.get(0).getLocation().getCity());
    }

    @Test
    void testFilterInstitutionByAddress() {
        // Given
        String address = "123 Main St";
        List<Institution> filteredInstitutions = Arrays.asList(institution);

        when(institutionRepository.findByLocationAddressContainingIgnoreCase(address)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(null, null, address, null);

        // Then
        verify(institutionRepository).findByLocationAddressContainingIgnoreCase(address);
        assertEquals(1, result.size());
        assertEquals("123 Main St", result.get(0).getLocation().getAddress());
    }

    @Test
    void testFilterInstitutionByMultipleCriteria() {
        // Given
        String name = "Test Institution";
        InstitutionsType type = InstitutionsType.HOSPITAL;
        CityEnum city = CityEnum.SKOPJE;
        String address = "123 Main St";
        List<Institution> filteredInstitutions = Arrays.asList(institution);

        when(institutionRepository.findByNameContainingIgnoreCaseAndTypeAndLocationCityAndLocationAddressContainingIgnoreCase(
                name, type, city, address)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(name, type, address, city);

        // Then
        verify(institutionRepository).findByNameContainingIgnoreCaseAndTypeAndLocationCityAndLocationAddressContainingIgnoreCase(
                name, type, city, address);
        assertEquals(1, result.size());
        assertEquals("Test Institution", result.get(0).getName());
        assertEquals(InstitutionsType.HOSPITAL, result.get(0).getType());
        assertEquals(CityEnum.SKOPJE, result.get(0).getLocation().getCity());
        assertEquals("123 Main St", result.get(0).getLocation().getAddress());
    }

    @Test
    void testFilterInstitution_NoResults() {
        // Given
        String nonExistingName = "Nonexistent Institution";
        List<Institution> filteredInstitutions = Arrays.asList();

        when(institutionRepository.findByNameContainingIgnoreCase(nonExistingName)).thenReturn(filteredInstitutions);

        // When
        List<Institution> result = institutionService.filterInstitution(nonExistingName, null, null, null);

        // Then
        assertTrue(result.isEmpty()); // Ensure no results are returned
    }
}
