package mk.finki.ukim.mk.crimsonheart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import util.SubmissionHelper;

import java.util.Arrays;
import java.util.List;

public class FilterLocationTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location1;
    private Location location2;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Ensure that Mockito initializes the mocks

        // Set up the test data
        location1 = new Location("123 Main St", CityEnum.SKOPJE, "Center", "1000", "North Macedonia");
        location1.setId(1L);
        location2 = new Location("456 Side St", CityEnum.TETOVO, "East", "2000", "North Macedonia");
        location2.setId(2L);
    }

    @Test
    public void test_filter_locations() {
        // Start the test
        SubmissionHelper.startTest("test-filter-locations");

        // Filter by address (text filter)
        when(locationRepository.filterLocations("123 Main St", CityEnum.SKOPJE)).thenReturn(Arrays.asList(location1));
        List<Location> locations = locationService.filterLocations("123 Main St", CityEnum.SKOPJE);
        assertEquals(1, locations.size(), "Expected 1 location with address containing '123 Main St'");
        assertEquals("123 Main St", locations.get(0).getAddress(), "Expected location address to be '123 Main St'");

        // Reset the filter
        when(locationRepository.filterLocations("456 Side St", CityEnum.TETOVO)).thenReturn(Arrays.asList(location2));
        locations = locationService.filterLocations("456 Side St", CityEnum.TETOVO);
        assertEquals(1, locations.size(), "Expected 1 location with address containing '456 Side St'");
        assertEquals("456 Side St", locations.get(0).getAddress(), "Expected location address to be '456 Side St'");

        // Filter by city (CityEnum.SKOPJE)
        when(locationRepository.filterLocations("", CityEnum.SKOPJE)).thenReturn(Arrays.asList(location1));
        locations = locationService.filterLocations("", CityEnum.SKOPJE);
        assertEquals(1, locations.size(), "Expected 1 location in SKOPJE");
        assertEquals("123 Main St", locations.get(0).getAddress(), "Expected location address to be '123 Main St'");

        // Filter with no matches (non-existent location)
        when(locationRepository.filterLocations("Non-existent Address", CityEnum.SKOPJE)).thenReturn(Arrays.asList());
        locations = locationService.filterLocations("Non-existent Address", CityEnum.SKOPJE);
        assertEquals(0, locations.size(), "Expected no locations for 'Non-existent Address'");

        // End the test
        SubmissionHelper.endTest();
    }
}
