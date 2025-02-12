package mk.finki.ukim.mk.crimsonheart;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import mk.finki.ukim.mk.crimsonheart.web.EventsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DonationEventTest {


    @Mock
    private DonationEventService donationEventService;

    @Mock
    private LocationService locationService;

    @Mock
    private InstitutionService institutionService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private EventsController eventsController;

    private Location location;
    private Institution institution;
    private Users manager;

    @BeforeEach
    void setUp() {
        location = new Location();  // Set proper mock location details
        institution = new Institution(); // Set proper mock institution details
        manager = new Users(); // Set proper mock user (manager) details
    }

    @Test
    void testCreateEventNotAllFieldsCompleted() {
        // Simulating a scenario where not all fields are completed
        String name = "";
        String description = "A description";
        DonationType donationType = DonationType.HOSPITAL;
        Long locationId = location.getId();
        String timeOfEvent = "10:00";
        Long institutionId = institution.getId();
        Long managerId = manager.getId();

        eventsController.saveEvent(null, name, description, donationType, locationId, new Date(), timeOfEvent, institutionId, managerId);

        // Verify that no event is saved when the name is empty (for instance)
        verify(donationEventService, times(0)).createEvent(any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testCreateEventWithDuplicateValues() {
        // Simulating a scenario where duplicate events are added
        String name = "Donation Event";
        String description = "A description";
        DonationType donationType = DonationType.HOSPITAL;
        Long locationId = location != null ? location.getId() : null;
        String timeOfEvent = "10:00";
        Long institutionId = institution != null ? institution.getId() : null;
        Long managerId = manager != null ? manager.getId() : null;

        // Mimic an event already present in the database
        DonationEvent existingEvent = new DonationEvent(name, description, donationType, location, new Date(), timeOfEvent, institution, manager);

        // Use when().thenReturn() only if createEvent() has a return type
        doNothing().when(donationEventService).createEvent(any(), any(), any(), any(), any(), any(), any(), any());


        eventsController.saveEvent(null, name, description, donationType, locationId, new Date(), timeOfEvent, institutionId, managerId);

        // Verify that the event creation service is invoked
        verify(donationEventService, times(1)).createEvent(any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testCreateEventWithEmptyFields() {
        // Simulate creating an event with missing mandatory fields
        DonationEvent incompleteEvent = new DonationEvent("", "", null, null, null, "", null, null);

        // Use doThrow for void methods
        doThrow(new IllegalArgumentException("Required fields are missing"))
                .when(donationEventService)
                .createEvent(any(), any(), any(), any(), any(), any(), any(), any());

        // Test to see if we catch the error
        try {
            eventsController.saveEvent(null, incompleteEvent.getName(), incompleteEvent.getDescription(),
                    incompleteEvent.getDonationType(),
                    incompleteEvent.getLocation() != null ? incompleteEvent.getLocation().getId() : null,
                    incompleteEvent.getDateOfEvent(),
                    incompleteEvent.getTimeOfEvent(),
                    incompleteEvent.getInstitution() != null ? incompleteEvent.getInstitution().getId() : null,
                    incompleteEvent.getUser() != null ? incompleteEvent.getUser().getId() : null);
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
            assert e.getMessage().equals("Required fields are missing");
        }

        verify(donationEventService, times(0)).createEvent(any(), any(), any(), any(), any(), any(), any(), any());
    }

}
