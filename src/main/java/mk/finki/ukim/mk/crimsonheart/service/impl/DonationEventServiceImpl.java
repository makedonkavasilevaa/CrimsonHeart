package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.exceptions.DonationEventNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.InstitutionNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.UsersNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonationEventServiceImpl implements DonationEventService {

    private final DonationEventRepository donationEventRepository;
    private final LocationRepository locationRepository;
    private final InstitutionRepository institutionRepository;
    private final UsersRepository usersRepository;

    public DonationEventServiceImpl(DonationEventRepository donationEventRepository, LocationRepository locationRepository, InstitutionRepository institutionRepository, UsersRepository usersRepository) {
        this.donationEventRepository = donationEventRepository;
        this.locationRepository = locationRepository;
        this.institutionRepository = institutionRepository;
        this.usersRepository = usersRepository;
    }


    @Override
    public void createEvent(String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager) {
        Location location = locationRepository.findById(locationId).orElseThrow( () -> new LocationNotFoundException(locationId));
        Institution institution = institutionRepository.findById(institutionId).orElseThrow( () -> new InstitutionNotFoundException(institutionId));
        Users user = usersRepository.findById(manager).orElseThrow(() -> new UsersNotFoundException(manager));
        DonationEvent donationEvent = new DonationEvent(name, description, donationType, location, dateOfEvent, timeOfEvent, institution, user);
        this.donationEventRepository.save(donationEvent);
    }

    @Override
    public void updateEvent(Long eventId,String name, String description, DonationType donationType, Long locationId, Date dateOfEvent, String timeOfEvent, Long institutionId, Long manager) {
        DonationEvent donationEvent = donationEventRepository.findById(eventId).orElseThrow(() -> new DonationEventNotFoundException(eventId));
        Location location = locationRepository.findById(locationId).orElseThrow( () -> new LocationNotFoundException(locationId));
        Institution institution = institutionRepository.findById(institutionId).orElseThrow( () -> new InstitutionNotFoundException(institutionId));
        Users user = usersRepository.findById(manager).orElseThrow(() -> new UsersNotFoundException(manager));

        donationEvent.setName(name);
        donationEvent.setDescription(description);
        donationEvent.setDonationType(donationType);
        donationEvent.setLocation(location);
        donationEvent.setDateOfEvent(dateOfEvent);
        donationEvent.setTimeOfEvent(timeOfEvent);
        donationEvent.setInstitution(institution);
        donationEvent.setUser(user);

        this.donationEventRepository.save(donationEvent);

    }

    @Override
    public List<DonationEvent> listAll() {
        return this.donationEventRepository.findAll();
    }

    @Override
    public DonationEvent findById(Long id) {
        DonationEvent event = this.donationEventRepository.findById(id).orElseThrow( () -> new DonationEventNotFoundException(id));
        return event;
    }

    @Override
    public void delete(Long id) {
        this.donationEventRepository.deleteById(id);
    }

    @Override
    public Optional<DonationEvent> searchEvents(String text) {
        if (text != null || !text.isEmpty()) {
            return this.donationEventRepository.findAllByNameContainsIgnoreCase(text);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DonationEvent> searchEventsByDonationType(DonationType donationType) {
        if (donationType != null) {
            return this.donationEventRepository.findAllByDonationType(donationType);
        }
        return Optional.empty();
    }
}
