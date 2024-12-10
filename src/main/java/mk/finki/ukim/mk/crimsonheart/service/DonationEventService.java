package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.Donation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DonationEventService {

    List<Donation> listAll();
    Optional<Donation> findById(Long id);
    Donation save(String name, String description, DonationType donationType, Long locationID, Date dateAndTime, Long institutionId, Long userId);
    void delete(Long id);
    Optional<Donation> searchEvents(String text);
    Optional<Donation> searchEventsByDonationType(DonationType donationType);
}
