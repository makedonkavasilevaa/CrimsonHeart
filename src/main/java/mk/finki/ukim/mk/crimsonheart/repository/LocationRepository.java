package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>, LocationCustomRepository {

    Optional<Location> findById(Long id);
    Optional<Location> findAllByAddressContainsIgnoreCase(String address);
    Optional<Location> findAllByCity(CityEnum city);
}