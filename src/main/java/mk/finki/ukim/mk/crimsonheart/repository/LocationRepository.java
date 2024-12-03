package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
