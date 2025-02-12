package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.exceptions.LocationNotFoundException;
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
    public void create(String address, CityEnum city, String state, String zip, String country) {
        Location location = new Location(address, city, state, zip, country);
        this.locationRepository.save(location);
    }

    @Override
    public void update(Long locationId, String address, CityEnum city, String state, String zip, String country) {
        Location location = this.locationRepository.findById(locationId).orElseThrow( () -> new LocationNotFoundException(locationId));
        location.setAddress(address);
        location.setCity(city);
        location.setState(state);
        location.setZip(zip);
        location.setCountry(country);
        this.locationRepository.save(location);
    }

    @Override
    public void delete(Long id) {
        Location location = this.locationRepository.findById(id).orElseThrow( () -> new LocationNotFoundException(id));
        if (location != null){
            this.locationRepository.delete(location);
        }
    }

    @Override
    public List<Location> listAll() {
        return this.locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        return this.locationRepository.findById(id).orElseThrow((() -> new LocationNotFoundException(id)));
    }

    @Override
    public Optional<Location> findByCity(CityEnum city) {
        return this.locationRepository.findAllByCity(city);
    }

    @Override
    public Optional<Location> findByAddress(String address) {
        return this.locationRepository.findAllByAddressContainsIgnoreCase(address);
    }

    @Override
    public List<Location> filterLocations(String address, CityEnum city) {
        return this.locationRepository.filterLocations(address, city);
    }
}
