package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationEventRepository extends JpaRepository<DonationEvent, Long> {
}
