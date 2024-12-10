package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Users;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UsersService {

    List<Users> listAll();
    Optional<Users> findById(Long id);
    Users save(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt);
    Optional<Users> findByRole(Roles role);
    Optional<Users> findByEmbg(String embg);
    Optional<Users> findAllByIsDonor();
    Optional<Users> findAllByLastDonationBefore(Sex sex, Date lastDonation);
    Optional<Users> findAllByLastDonationBeforeAndIsDonorAndBloodType(Sex sex, Date lastDonation, BloodType bloodType);
    Optional<Users> findAllByBloodType(BloodType bloodType);
    Optional<Users> findAllByName(String name);
    Optional<Users> findAllByTimesRejected(Integer timesRejected);

}