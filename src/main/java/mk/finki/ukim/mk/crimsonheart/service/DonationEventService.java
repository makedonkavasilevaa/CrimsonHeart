package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DonationEventService {

    void createEvent(String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager);
    void updateEvent(Long eventId, String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager);
    List<DonationEvent> listAll();
    DonationEvent findById(Long id);
    void delete(Long id);
    Optional<DonationEvent> searchEvents(String text);
    Optional<DonationEvent> searchEventsByDonationType(DonationType donationType);
}
