package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.*;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final InstitutionRepository institutionRepository;
    private final LocationRepository locationRepository;

    public UsersServiceImpl(UsersRepository usersRepository, InstitutionRepository institutionRepository, LocationRepository locationRepository) {
        this.usersRepository = usersRepository;
        this.institutionRepository = institutionRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Users> listAll() {
        return this.usersRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return this.usersRepository.findById(id);
    }

    @Override
    public Users save(Roles role, String name, String surname, Date birthday, Sex sex, String email, String phone, String embg, Long locationId, BloodType bloodType, boolean isDonor, Date lastDonation, Long worksAt) {
        Location location = this.locationRepository.findById(locationId).orElseThrow();
        Institution institution = this.institutionRepository.findById(worksAt).orElseThrow();
        if (embg.length() !=  13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }

        Users user = new Users(role, name, surname, birthday, sex, email, phone, embg, location, bloodType, isDonor, lastDonation,institution);

        return this.usersRepository.save(user);
    }

    @Override
    public Optional<Users> findByRole(Roles role) {
        return this.usersRepository.findAllByRole(role);
    }

    @Override
    public Optional<Users> findByEmbg(String embg) {
        if (embg.length() != 13){
            throw new IllegalArgumentException("EMBG isn't in the correct format");
        }
        return this.usersRepository.findAllByEmbg(embg);
    }

    @Override
    public Optional<Users> findAllByIsDonor() {
        return this.usersRepository.findAllByIsDonorTrue();
    }

    @Override
    public Optional<Users> findAllByLastDonationBefore(Sex sex, Date lastDonation) {
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
    public Optional<Users> findAllByLastDonationBeforeAndIsDonorAndBloodType(Sex sex, Date lastDonation,BloodType bloodType) {

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
    public Optional<Users> findAllByBloodType(BloodType bloodType) {
        return this.usersRepository.findAllByBloodType(bloodType);
    }

    @Override
    public Optional<Users> findAllByName(String name) {
        return this.usersRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Users> findAllByTimesRejected(Integer timesRejected) {
        return this.usersRepository.findAllByTimesRejectedLessThan(timesRejected);
    }
}
