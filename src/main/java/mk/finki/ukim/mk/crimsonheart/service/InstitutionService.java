package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;

import java.util.List;
import java.util.Optional;

public interface InstitutionService {

    void create(String name, String phone, String email, InstitutionsType institutionsType, Long locationId);
    void update(Long id, String name, String phone, String email, InstitutionsType institutionsType, Long locationId);
    void delete(Long id);
    List<Institution> listAll();
    Institution findById(Long id);
    Optional<Institution> findByName(String name);
    Optional<Institution> findByInstitutionType(InstitutionsType institutionsType);
    Optional<Institution> findAllByCity(CityEnum cityEnum);
}
