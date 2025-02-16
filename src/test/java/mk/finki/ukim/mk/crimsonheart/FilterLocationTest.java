package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.LocationServiceImpl;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
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
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository; // Mock the repository

    @InjectMocks
    private LocationServiceImpl locationService; // Inject the mock repository into the service

    @BeforeEach
    void setUp() {
        // No need for manual instantiation, @InjectMocks handles it
    }

    @Test
    void testFilterLocationsByAddress() {
        // Given
        String address = "123 Main St";
        List<Location> filteredLocations = Arrays.asList(
                new Location(address, CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia")
        );

        // When
        when(locationRepository.findByAddressContainingIgnoreCase(address)).thenReturn(filteredLocations);

        // Call service method to filter locations by address
        List<Location> result = locationService.filterLocations(address, null, null);

        // Then
        verify(locationRepository).findByAddressContainingIgnoreCase(address); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of locations
        assertEquals(address, result.get(0).getAddress()); // Verify correct location was returned
    }

    @Test
    void testFilterLocationsByCity() {
        // Given
        CityEnum city = CityEnum.SKOPJE;
        List<Location> filteredLocations = Arrays.asList(
                new Location("123 Main St", city, "Skopje", "1000", "North Macedonia")
        );

        // When
        when(locationRepository.findByCity(city)).thenReturn(filteredLocations);

        // Call service method to filter locations by city
        List<Location> result = locationService.filterLocations(null, city, null);

        // Then
        verify(locationRepository).findByCity(city); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of locations
        assertEquals(city, result.get(0).getCity()); // Verify correct city
    }

    @Test
    void testFilterLocationsByZip() {
        // Given
        String zip = "1000";
        List<Location> filteredLocations = Arrays.asList(
                new Location("123 Main St", CityEnum.SKOPJE, "Skopje", zip, "North Macedonia")
        );

        // When
        when(locationRepository.findByZip(zip)).thenReturn(filteredLocations);

        // Call service method to filter locations by zip
        List<Location> result = locationService.filterLocations(null, null, zip);

        // Then
        verify(locationRepository).findByZip(zip); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of locations
        assertEquals(zip, result.get(0).getZip()); // Verify correct zip
    }

    @Test
    void testFilterLocations_NoResults() {
        // Given
        String nonExistingAddress = "Nonexistent Address";
        List<Location> filteredLocations = Arrays.asList();

        // When
        when(locationRepository.findByAddressContainingIgnoreCase(nonExistingAddress)).thenReturn(filteredLocations);

        // Call service method to filter locations by address
        List<Location> result = locationService.filterLocations(nonExistingAddress, null, null);

        // Then
        assertTrue(result.isEmpty()); // Ensure no locations are returned
    }

    @Test
    void testFilterLocations_AllFilters() {
        // Given
        String address = "Main St";
        CityEnum city = CityEnum.SKOPJE;
        String zip = "1000";
        List<Location> filteredLocations = Arrays.asList(
                new Location(address, city, "Skopje", zip, "North Macedonia")
        );

        // When
        when(locationRepository.findByAddressContainingIgnoreCase(address)).thenReturn(filteredLocations);

        // Call service method to filter locations by address, city, and zip
        List<Location> result = locationService.filterLocations(address, city, zip);

        // Then
        verify(locationRepository).findByAddressContainingIgnoreCase(address); // Ensure repository was called
        assertEquals(1, result.size()); // Ensure we got the expected number of locations
        assertEquals(address, result.get(0).getAddress()); // Verify correct location was returned
        assertEquals(city, result.get(0).getCity()); // Verify correct city
        assertEquals(zip, result.get(0).getZip()); // Verify correct zip
    }
}
