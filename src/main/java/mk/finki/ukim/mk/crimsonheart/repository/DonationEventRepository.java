package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationEventRepository extends JpaRepository<Donation, Long> {

    Optional<Donation> findById(Long id);
    Optional<Donation> findAllByNameContainsIgnoreCase(String name);
    Optional<Donation> findAllByDonationType(DonationType donationType);
}
