package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionService {

    List<Institution> listAll();
    Optional<Institution> findById(Long id);
    Optional<Institution> findByName(String name);
    Optional<Institution> findByInstitutionType(InstitutionsType institutionsType);
    Optional<Institution> findAllByCity(CityEnum cityEnum);
}
