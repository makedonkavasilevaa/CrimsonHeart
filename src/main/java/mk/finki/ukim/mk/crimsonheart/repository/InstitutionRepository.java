package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Optional<Institution> findById(Long id);
    Optional<Institution> findAllByInstitutionsType(InstitutionsType institutionsType);
    Optional<Institution> findByNameContainingIgnoreCase(String name);
    Optional<Institution> findAllByLocationCity(CityEnum city);
}
