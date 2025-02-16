package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DonationEventServiceTest {

    @Mock
    private DonationEventRepository donationEventRepository;

    @InjectMocks
    private DonationEventService donationEventService;

    private Institution institution;
    private Users user;
    private Location location;
    private DonationEvent donationEvent;
    private DonationEvent donationEvent2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Create test data
        institution = new Institution("Institution A", "Address A");
        user = new Users("Admin", "admin@example.com", null);  // Simplified user model
        location = new Location("Location A", "Address A");

        donationEvent = new DonationEvent("Blood Drive", "Blood donation event", DonationType.BLOOD, location, new Date(), "10:00", institution, user);
        donationEvent2 = new DonationEvent("Plasma Drive", "Plasma donation event", DonationType.PLASMA, location, new Date(), "12:00", institution, user);
    }

    @Test
    public void testFindByName() {
        // Given
        when(donationEventRepository.findByName("Blood Drive")).thenReturn(Arrays.asList(donationEvent));

        // When
        List<DonationEvent> events = donationEventService.findByName("Blood Drive");

        // Then
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals("Blood Drive", events.get(0).getName());
    }

    @Test
    public void testFindByDonationType() {
        // Given
        when(donationEventRepository.findByDonationType(DonationType.BLOOD)).thenReturn(Arrays.asList(donationEvent));

        // When
        List<DonationEvent> events = donationEventService.findByDonationType(DonationType.BLOOD);

        // Then
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(DonationType.BLOOD, events.get(0).getDonationType());
    }

    @Test
    public void testFindByLocation() {
        // Given
        when(donationEventRepository.findByLocation(location)).thenReturn(Arrays.asList(donationEvent, donationEvent2));

        // When
        List<DonationEvent> events = donationEventService.findByLocation(location);

        // Then
        assertNotNull(events);
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> "Blood Drive".equals(e.getName())));
        assertTrue(events.stream().anyMatch(e -> "Plasma Drive".equals(e.getName())));
    }

    @Test
    public void testFindByDateOfEvent() {
        // Given
        Date date = new Date();
        when(donationEventRepository.findByDateOfEvent(date)).thenReturn(Arrays.asList(donationEvent));

        // When
        List<DonationEvent> events = donationEventService.findByDateOfEvent(date);

        // Then
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(date, events.get(0).getDateOfEvent());
    }

    @Test
    public void testFindByDescription() {
        // Given
        when(donationEventRepository.findByDescription("Blood donation event")).thenReturn(Arrays.asList(donationEvent));

        // When
        List<DonationEvent> events = donationEventService.findByDescription("Blood donation event");

        // Then
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals("Blood donation event", events.get(0).getDescription());
    }

    @Test
    public void testFindAllDonationEvents() {
        // Given
        when(donationEventRepository.findAll()).thenReturn(Arrays.asList(donationEvent, donationEvent2));

        // When
        List<DonationEvent> events = donationEventService.listAll();

        // Then
        assertNotNull(events);
        assertEquals(2, events.size());
    }
}
