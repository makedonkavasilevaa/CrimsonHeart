package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.*;
import mk.finki.ukim.mk.crimsonheart.exceptions.UsersNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private InstitutionService institutionService;

    @GetMapping("")
    public String getUsersPage(@RequestParam(required = false) String error,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String embg,
                               @RequestParam(required = false) BloodType bloodType,
                               @RequestParam(required = false) Roles roles,
                               Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Users> users = new ArrayList<>();

        if (name != null || embg != null || bloodType != null || roles != null) {
            users =  this.usersService.filterUsers(name, embg, roles, bloodType);
        }else
            users = this.usersService.listAll();


        List<Location> locations = this.locationService.listAll();
        List<BloodType> bloodTypes = Arrays.stream(BloodType.values()).toList();
        List<Roles> role = Arrays.stream(Roles.values()).toList();
        List<Sex> sexes = Arrays.stream(Sex.values()).toList();

        model.addAttribute("bodyContent", "locations");
        model.addAttribute("users", users);
        model.addAttribute("roles", role);
        model.addAttribute("locations", locations);
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("sexes", sexes);
        return "users";
    }

    @GetMapping("/patients")
    public String getPatientsPage(@RequestParam(required = false) String error,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String embg,
                                  @RequestParam(required = false) BloodType bloodType,
                                  Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Users> users = new ArrayList<>();

        if (name != null || embg != null || bloodType != null) {
            users =  this.usersService.filterUsers(name, embg, Roles.PATIENT, bloodType);
        }else
            users = this.usersService.findByRole(Roles.PATIENT);


        List<Location> locations = this.locationService.listAll();
        List<BloodType> bloodTypes = List.of(BloodType.values());
        List<Sex> sexes = List.of(Sex.values());

        List<EmploymentStatus> employmentStatuses = List.of(EmploymentStatus.values());
        model.addAttribute("bodyContent", "locations");
        model.addAttribute("users", users);
        model.addAttribute("locations", locations);
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("sexes", sexes);
        model.addAttribute("status", employmentStatuses);
        return "patients";
    }

    @GetMapping("/add-form")
    public String getAddUserPage(Model model) {
        List<Location> locations = this.locationService.listAll();
        List<Roles> roles = List.of(Roles.values());
        List<BloodType> bloodTypes = List.of(BloodType.values());
        List<Sex> sexes = List.of(Sex.values());
        List<Institution> institutions = this.institutionService.listAll();

        model.addAttribute("locations", locations);
        model.addAttribute("institutions", institutions);
        model.addAttribute("roles", roles);
        model.addAttribute("sexes", sexes);
        model.addAttribute("bloodTypes", bloodTypes);

        return "add-user";
    }

    @GetMapping({"/add-patient", "/users/add-patient"})
    public String getAddPatientPage(Model model) {
        List<Location> locations = this.locationService.listAll();
        List<Roles> roles = List.of(Roles.values());
        List<BloodType> bloodTypes = List.of(BloodType.values());
        List<Sex> sexes = List.of(Sex.values());
        List<Institution> institutions = this.institutionService.listAll();
        List<EmploymentStatus> employmentStatuses = List.of(EmploymentStatus.values());

        model.addAttribute("locations", locations);
        model.addAttribute("institutions", institutions);
        model.addAttribute("roles", roles);
        model.addAttribute("sexes", sexes);
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("status", employmentStatuses);

        return "add-patient";
    }


    @PostMapping("/add")
    public String saveUser(@RequestParam(required = false) Long id,
                               @RequestParam Roles role,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                               @RequestParam Sex sex,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String embg,
                               @RequestParam Long locationId,
                           @RequestParam BloodType bloodType,
                           @RequestParam (defaultValue = "false") Boolean isDonor,
                           @RequestParam(required = false) Date lastDonation,
                           @RequestParam Long worksAtId) {
        if (id != null && id > 0) {
            this.usersService.update(id, role, name, surname, birthday, sex, email, phone, embg, locationId, bloodType, isDonor, lastDonation, worksAtId);
        }else
            this.usersService.create(role, name, surname, birthday, sex, email, phone, embg, locationId, bloodType, isDonor, lastDonation, worksAtId);
        return "redirect:/users";
    }

    @PostMapping("/add-patients")
    public String savePatient(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                              @RequestParam Sex sex,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam String embg,
                              @RequestParam Long locationId,
                              @RequestParam BloodType bloodType,
                              @RequestParam (defaultValue = "false") Boolean isDonor,
                              @RequestParam(required = false) Date lastDonation,
                              @RequestParam EmploymentStatus employmentStatus) {
        if (id != null && id > 0) {
            this.usersService.updatePatient(id, Roles.PATIENT, name, surname, birthday, sex, email, phone, embg, locationId, bloodType, isDonor, lastDonation, employmentStatus);
        }else
            this.usersService.createPatient(Roles.PATIENT, name, surname, birthday, sex, email, phone, embg, locationId, bloodType, isDonor, lastDonation, employmentStatus);
        return "redirect:/users";
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        this.usersService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        if (this.usersService.findById(userId) != null) {
            Users user = this.usersService.findById(userId);
            List<BloodType> bloodTypes = List.of(BloodType.values());
            List<Roles> roles = List.of(Roles.values());
            List<Location> locations = this.locationService.listAll();
            List<Institution> institutions = this.institutionService.listAll();
            List<Sex> sexes = List.of(Sex.values());

            model.addAttribute("user", user);
            model.addAttribute("bloodTypes", bloodTypes);
            model.addAttribute("locations", locations);
            model.addAttribute("institutions", institutions);
            model.addAttribute("sexes", sexes);
            model.addAttribute("roles", roles);
            return "add-user";
        }
        return "redirect:/users?error=UserNotFound";
    }
}
