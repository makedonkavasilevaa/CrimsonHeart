package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {  @Mock
private LocationRepository locationRepository; // Mock the repository

    @InjectMocks
    private LocationServiceImpl locationService; // Inject the mock into the service

    @BeforeEach
    void setUp() {
        // No longer need to manually create the service since @InjectMocks handles it
    }

    @Test
    void testAddLocation_Success() {
        // Setup mock data
        Location location = new Location("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");

        // Mock repository save
        when(locationRepository.save(location)).thenReturn(location);

        // Add location
        locationService.create("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");

        // Verify that save was called
        verify(locationRepository).save(location);
    }

    @Test
    void testAddLocation_IncompleteFields_Fail() {
        // Test missing address
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");
        });

        // Test missing city
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("123 Main St", null, "Skopje", "1000", "North Macedonia");
        });

        // Test missing zip
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("123 Main St", CityEnum.SKOPJE, "Skopje", "", "North Macedonia");
        });

        // Test missing country
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "");
        });
    }

    @Test
    void testAddLocation_DuplicateAddress_Fail() {
        // Setup mock data
        Location location1 = new Location("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");

        // Mock repository to return location1 when querying by address
        when(locationRepository.findAllByAddressContainsIgnoreCase("123 Main St")).thenReturn(java.util.Optional.of(location1));

        // Test duplicate address
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");
        });
    }

    @Test
    void testAddLocation_DuplicateCity_Fail() {
        // Setup mock data
        Location location1 = new Location("123 Main St", CityEnum.SKOPJE, "Skopje", "1000", "North Macedonia");

        // Mock repository to return location1 when querying by city
        when(locationRepository.findAllByCity(CityEnum.SKOPJE)).thenReturn(java.util.Optional.of(location1));

        // Test duplicate city
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.create("456 Elm St", CityEnum.SKOPJE, "Skopje", "2000", "North Macedonia");
        });
    }
}
