package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;

import java.util.List;

public interface InstitutionCustomRepository {

    List<Institution> filterInstitutions(String name, InstitutionsType type, String address, CityEnum city);
}
