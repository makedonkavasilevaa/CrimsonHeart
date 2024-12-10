package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.Donation;
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
    public List<Donation> listAll() {
        return List.of();
    }

    @Override
    public Optional<Donation> findById(Long id) {
        return donationEventRepository.findById(id);
    }

    @Override
    public Donation save(String name, String description, DonationType donationType, Long locationId, Date dateAndTime, Long institutionId, Long userId) {
        Location location = locationRepository.findById(locationId).orElseThrow();
        Institution institution = institutionRepository.findById(institutionId).orElseThrow();
        Users user = usersRepository.findById(userId).orElseThrow();

        Donation donation = new Donation(name, description, donationType, location, dateAndTime, institution, user);

        return this.donationEventRepository.save(donation);
    }

    @Override
    public void delete(Long id) {
        this.donationEventRepository.deleteById(id);
    }

    @Override
    public Optional<Donation> searchEvents(String text) {
        if (text != null || !text.isEmpty()) {
            return this.donationEventRepository.findAllByNameContainsIgnoreCase(text);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Donation> searchEventsByDonationType(DonationType donationType) {
        if (donationType != null) {
            return this.donationEventRepository.findAllByDonationType(donationType);
        }
        return Optional.empty();
    }
}
