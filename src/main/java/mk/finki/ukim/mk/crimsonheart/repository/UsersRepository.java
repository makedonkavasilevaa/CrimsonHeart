package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import org.aspectj.weaver.patterns.IScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAll();
    Optional<Users> findById(Long id);
    Optional<Users> findByNameContainingIgnoreCase(String username);
    Optional<Users> findAllByRole(Roles role);
    Optional<Users> findAllByBloodType(BloodType bloodType);
    Optional<Users> findAllByTimesRejectedLessThan(Integer timesRejected);
    Optional<Users> findAllByEmbg(String embg);
    Optional<Users> findAllByIsDonorTrue();
    Optional<Users> findAllByLastDonationBeforeAndIsDonorTrue(Date lastDonation);
    Optional<Users> findAllByLastDonationBeforeAndIsDonorTrueAndBloodType(Date lastDonation, BloodType bloodType);
}
