package mk.finki.ukim.mk.crimsonheart.service.impl;

import lombok.NonNull;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.EmploymentStatus;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.exceptions.*;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.*;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final InstitutionRepository institutionRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, InstitutionRepository institutionRepository, LocationRepository locationRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.institutionRepository = institutionRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> listAll() {
        return this.usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return this.usersRepository.findById(id).orElseThrow(() -> new UsersNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        this.usersRepository.deleteById(id);
    }

    @Override
    public void create(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        Institution institution = this.institutionRepository.findById(worksAt).orElseThrow( () -> new InstitutionNotFoundException(worksAt));
        if (embg.length() !=  13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }

        Users user = new Users(role, name, surname, birthday, sex, email, phone, embg, location, bloodType, isDonor, lastDonation, institution);

        this.usersRepository.save(user);
    }

    @Override
    public void update(Long userId, Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt) {
        Location location = this.locationRepository.findById(locationId).orElseThrow( () -> new LocationNotFoundException(locationId));
        Institution institution = this.institutionRepository.findById(worksAt).orElseThrow( () -> new InstitutionNotFoundException(worksAt));
        if (embg.length() !=  13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }

        Users user = this.usersRepository.findById(userId).orElseThrow(() -> new UsersNotFoundException(userId));

        user.setRole(role);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setEmbg(embg);
        user.setLocation(location);
        user.setBloodType(bloodType);
        user.setDonor(isDonor);
        user.setLastDonation(lastDonation);
        user.setWorksAt(institution);

        this.usersRepository.save(user);

    }

    @Override
    public void createPatient(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, EmploymentStatus employmentStatus) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        if (embg.length() !=  13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }

        Users user = new Users(role, name, surname, birthday, sex, email, phone, embg, location, bloodType, isDonor, lastDonation, employmentStatus);

        this.usersRepository.save(user);
    }

    @Override
    public void updatePatient(Long userId, Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, EmploymentStatus employmentStatus) {
        Location location = this.locationRepository.findById(locationId).orElseThrow( () -> new LocationNotFoundException(locationId));
        if (embg.length() !=  13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }

        Users user = this.usersRepository.findById(userId).orElseThrow(() -> new UsersNotFoundException(userId));

        user.setRole(role);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setEmbg(embg);
        user.setLocation(location);
        user.setBloodType(bloodType);
        user.setDonor(isDonor);
        user.setLastDonation(lastDonation);
        user.setEmploymentStatus(employmentStatus);

        this.usersRepository.save(user);
    }

    @Override
    public List<Users> findByRole(Roles role) {
        return this.usersRepository.findAllByRoleEquals(role);
    }

    @Override
    public Users findByEmbg(String embg) {
        if (embg.length() != 13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }
        return this.usersRepository.findAllByEmbg(embg);
    }

    @Override
    public List<Users> findAllByIsDonor() {
        return this.usersRepository.findAllByIsDonorTrue();
    }

    @Override
    public List<Users> findAllByLastDonationBefore(Sex sex, Date lastDonation) {
        Calendar calendar = Calendar.getInstance();
        if (sex == Sex.MALE) {
            calendar.add(Calendar.MONTH, -3);
        } else if (sex == Sex.FEMALE) {
            calendar.add(Calendar.MONTH, -4);
        }
        Date thresholdDate = calendar.getTime();

        return this.usersRepository.findAllByLastDonationBeforeAndIsDonorTrue(thresholdDate);
    }

    @Override
    public List<Users> findAllByLastDonationBeforeAndIsDonorAndBloodType(Sex sex, Date lastDonation,BloodType bloodType) {

        Calendar calendar = Calendar.getInstance();
        if (sex == Sex.MALE) {
            calendar.add(Calendar.MONTH, -3);
        } else if (sex == Sex.FEMALE) {
            calendar.add(Calendar.MONTH, -4);
        }
        Date thresholdDate = calendar.getTime();

        return this.usersRepository.findAllByLastDonationBeforeAndIsDonorTrueAndBloodType(thresholdDate, bloodType);
    }

    @Override
    public List<Users> findAllByBloodType(BloodType bloodType) {
        return this.usersRepository.findAllByBloodType(bloodType);
    }

    @Override
    public List<Users> findAllByName(String name) {
        return this.usersRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Users> findAllByTimesRejected(Integer timesRejected) {
        return this.usersRepository.findAllByTimesRejectedLessThan(timesRejected);
    }

    @Override
    public List<Users> filterUsers(String name, String embg, Roles roles, BloodType bloodType) {
        return this.usersRepository.getUsersByFilter(roles, name, embg, bloodType);
    }

    @Override
    public void register(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, String password, String repeatedPassword) {

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatedPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.usersRepository.findAllByEmail(email).isPresent()) {
            throw new UsernameAlreadyExistsException(email);
        }
        if (role == null || role.equals("")){
            role = Roles.PATIENT;
        }

        Users user = new Users(role, name, surname, birthday, sex, email, passwordEncoder.encode(password), phone,  embg);

        usersRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword, String repeatedPassword) {
        if (userId == null || newPassword == null || newPassword.isEmpty() || repeatedPassword == null || repeatedPassword.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        Users user = this.usersRepository.findById(userId).orElseThrow(() -> new UsersNotFoundException(userId));

        if (user != null) {
            String storedPasswordHash = user.getPassword();

            // Check if the old password matches the stored password
            if (passwordEncoder.matches(newPassword, storedPasswordHash)) {
                throw new PasswordsAreTheSameException();
            }

            // Ensure newPassword and repeatedPassword match
            if (!newPassword.equals(repeatedPassword)) {
                throw new PasswordsDoNotMatchException();
            }

            // Encode and save the new password
            user.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findAllByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
