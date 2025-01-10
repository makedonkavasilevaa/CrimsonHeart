package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;

import java.util.Date;
import java.util.List;

public interface DonationEventService {

    void createEvent(String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager);
    void updateEvent(Long eventId, String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager);
    List<DonationEvent> listAll();
    DonationEvent findById(Long id);
    void delete(Long id);
    List<DonationEvent> searchEvents(String text);
    List<DonationEvent> searchEventsByDonationType(DonationType donationType);
}
