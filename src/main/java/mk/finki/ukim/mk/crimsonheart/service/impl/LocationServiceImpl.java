package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.LocationRepository;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(String address, CityEnum city, String state, String zip, String country) {
        Location location = new Location(address, city, state, zip, country);
        return this.locationRepository.save(location);
    }

    @Override
    public void delete(Long id) {
        Location location = this.locationRepository.findById(id).orElse(null);
        this.locationRepository.delete(location);
    }

    @Override
    public List<Location> listAll() {
        return this.locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return this.locationRepository.findById(id);
    }

    @Override
    public Optional<Location> findByCity(CityEnum city) {
        return this.locationRepository.findAllByCity(city);
    }

    @Override
    public Optional<Location> findByAddress(String address) {
        return this.locationRepository.findAllByAddressContainsIgnoreCase(address);
    }
}
