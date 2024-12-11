package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationEventRepository extends JpaRepository<DonationEvent, Long> {

    Optional<DonationEvent> findById(Long id);
    Optional<DonationEvent> findAllByNameContainsIgnoreCase(String name);
    Optional<DonationEvent> findAllByDonationType(DonationType donationType);
}
