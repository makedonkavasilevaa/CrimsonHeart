package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAll();
    void deleteById(Long id);
    Optional<Users> findById(Long id);
    List<Users> findByNameContainingIgnoreCase(String name);
    List<Users> findAllByRoleEquals(Roles role);
    List<Users> findAllByBloodType(BloodType bloodType);
    List<Users> findAllByTimesRejectedLessThan(Integer timesRejected);
    Users findAllByEmbg(String embg);
    List<Users> findAllByIsDonorTrue();
    List<Users> findAllByLastDonationBeforeAndIsDonorTrue(Date lastDonation);
    List<Users> findAllByLastDonationBeforeAndIsDonorTrueAndBloodType(Date lastDonation, BloodType bloodType);
    Optional<Users> findAllByEmail(String email);
    Optional<Users> findAllByEmailAndPassword(String email, String password);
}
