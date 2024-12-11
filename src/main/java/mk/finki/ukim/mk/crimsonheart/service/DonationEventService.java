package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DonationEventService {

    List<DonationEvent> listAll();
    Optional<DonationEvent> findById(Long id);
    DonationEvent save(String name, String description, DonationType donationType, Long locationID, Date dateAndTime, Long institutionId, Long userId);
    void delete(Long id);
    Optional<DonationEvent> searchEvents(String text);
    Optional<DonationEvent> searchEventsByDonationType(DonationType donationType);
}
