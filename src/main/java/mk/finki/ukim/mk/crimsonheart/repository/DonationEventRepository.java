package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationEventRepository extends JpaRepository<DonationEvent, Long>, DonationEventCustomRepository {

    List<DonationEvent> findAllByNameContainsIgnoreCase(String name);
    List<DonationEvent> findAllByDonationType(DonationType donationType);
    List<DonationEvent> findDonationEventById(Long id);
}
