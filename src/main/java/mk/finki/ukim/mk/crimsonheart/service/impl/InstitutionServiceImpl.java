package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.exceptions.InstitutionNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final LocationRepository locationRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository, LocationRepository locationRepository) {
        this.institutionRepository = institutionRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void create(String name, String phone, String email, InstitutionsType institutionsType, Long locationId) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        Institution institution = new Institution(name, phone, email, institutionsType, location);

        this.institutionRepository.save(institution);
    }

    @Override
    public void update(Long id, String name, String phone, String email, InstitutionsType institutionsType, Long locationId) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        Institution institution = this.institutionRepository.findById(id).orElseThrow(() -> new InstitutionNotFoundException(id));

        institution.setName(name);
        institution.setPhone(phone);
        institution.setEmail(email);
        institution.setInstitutionsType(institutionsType);
        institution.setLocation(location);

        this.institutionRepository.save(institution);
    }

    @Override
    public List<Institution> listAll() {
        return this.institutionRepository.findAll();
    }

    @Override
    public Institution findById(Long id) {
        return this.institutionRepository.findById(id).orElseThrow(() -> new InstitutionNotFoundException(id));
    }

    @Override
    public Optional<Institution> findByName(String name) {
        return this.institutionRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Institution> findByInstitutionType(InstitutionsType institutionsType) {
        return this.institutionRepository.findAllByInstitutionsType(institutionsType);
    }

    @Override
    public Optional<Institution> findAllByCity(CityEnum cityEnum) {
        return this.institutionRepository.findAllByLocationCity(cityEnum);
    }
}
