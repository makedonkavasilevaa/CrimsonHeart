package mk.finki.ukim.mk.crimsonheart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.DonationEventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import util.SubmissionHelper;

import java.util.Arrays;
import java.util.List;

public class FilterEventTest {

    @Mock
    private DonationEventRepository donationEventRepository;

    @InjectMocks
    private DonationEventServiceImpl donationEventService;

    private DonationEvent event1;
    private DonationEvent event2;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Ensure that Mockito initializes the mocks

        // Set up the test data
        event1 = new DonationEvent("Blood Donation", "Description of blood donation", DonationType.HUMANITARIAN_EVENT, null, null, null, null, null);
        event1.setId(1L);
        event2 = new DonationEvent("Plasma Donation", "Description of plasma donation", DonationType.HOSPITAL, null, null, null, null, null);
        event2.setId(2L);
    }

    @Test
    public void test_filter_events() {
        // Start the test
        SubmissionHelper.startTest("test-filter-events");

        // Filter by name (text filter)
        when(donationEventRepository.filterEvents("Blood", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE)).thenReturn(Arrays.asList(event1));
        List<DonationEvent> events = donationEventService.filterEvents("Blood", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE);
        assertEquals(1, events.size(), "Expected 1 event with name containing 'Blood'");
        assertEquals("Blood Donation", events.get(0).getName(), "Expected event name to be 'Blood Donation'");

        // Reset the filter
        when(donationEventRepository.filterEvents("Plasma", DonationType.HOSPITAL, CityEnum.SKOPJE)).thenReturn(Arrays.asList(event2));
        events = donationEventService.filterEvents("Plasma", DonationType.HOSPITAL, CityEnum.SKOPJE);
        assertEquals(1, events.size(), "Expected 1 event with name containing 'Plasma'");
        assertEquals("Plasma Donation", events.get(0).getName(), "Expected event name to be 'Plasma Donation'");

        // Filter by donation type (DonationType.HOSPITAL)
        when(donationEventRepository.filterEvents("", DonationType.HOSPITAL, CityEnum.SKOPJE)).thenReturn(Arrays.asList(event2));
        events = donationEventService.filterEvents("", DonationType.HOSPITAL, CityEnum.SKOPJE);
        assertEquals(1, events.size(), "Expected 1 event of type Plasma");
        assertEquals("Plasma Donation", events.get(0).getName(), "Expected event name to be 'Plasma Donation'");

        // Filter by city (CityEnum.SKOPJE)
        when(donationEventRepository.filterEvents("", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE)).thenReturn(Arrays.asList(event1));
        events = donationEventService.filterEvents("", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE);
        assertEquals(1, events.size(), "Expected 1 event in SKOPJE");
        assertEquals("Blood Donation", events.get(0).getName(), "Expected event name to be 'Blood Donation'");

        // Filter with no matches (non-existent event)
        when(donationEventRepository.filterEvents("Non-existent Event", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE)).thenReturn(Arrays.asList());
        events = donationEventService.filterEvents("Non-existent Event", DonationType.HUMANITARIAN_EVENT, CityEnum.SKOPJE);
        assertEquals(0, events.size(), "Expected no events for 'Non-existent Event'");

        // End the test
        SubmissionHelper.endTest();
    }
}
