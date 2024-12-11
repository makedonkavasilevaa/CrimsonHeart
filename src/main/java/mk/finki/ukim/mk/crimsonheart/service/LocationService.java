package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location save(String address, CityEnum city, String state, String zip, String country);
    void delete(Long id);
    List<Location> listAll();
    Optional<Location> findById(Long id);
    Optional<Location> findByCity(CityEnum city);
    Optional<Location> findByAddress(String address);
}
