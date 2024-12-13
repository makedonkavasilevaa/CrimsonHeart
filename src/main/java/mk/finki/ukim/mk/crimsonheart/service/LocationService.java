package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    void create(String address, CityEnum city, String state, String zip, String country);
    void update(Long locationId, String address, CityEnum city, String state, String zip, String country);
    void delete(Long id);
    List<Location> listAll();
    Location findById(Long id);
    Optional<Location> findByCity(CityEnum city);
    Optional<Location> findByAddress(String address);
}
