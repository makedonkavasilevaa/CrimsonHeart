package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.EmploymentStatus;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;
import java.util.List;

public interface UsersService extends UserDetailsService {

    List<Users> listAll();
    Users findById(Long id);
    void delete(Long id);
    void create(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt);
    void update(Long userId, Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt);
    void createPatient(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, EmploymentStatus employmentStatus);
    void updatePatient(Long userId, Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, EmploymentStatus employmentStatus);
    List<Users> findByRole(Roles role);
    Users findByEmbg(String embg);
    List<Users> findAllByIsDonor();
    List<Users> findAllByLastDonationBefore(Sex sex, Date lastDonation);
    List<Users> findAllByLastDonationBeforeAndIsDonorAndBloodType(Sex sex, Date lastDonation, BloodType bloodType);
    List<Users> findAllByBloodType(BloodType bloodType);
    List<Users> findAllByName(String name);
    List<Users> findAllByTimesRejected(Integer timesRejected);

    void register(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, String password, String repeatedPassword);
}