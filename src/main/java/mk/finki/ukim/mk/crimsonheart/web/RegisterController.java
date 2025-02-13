package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.exceptions.UsernameAlreadyExistsException;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.service.AuthService;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UsersService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private InstitutionService institutionService;

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        List<Roles> roles = List.of(Roles.values());
        List<BloodType> bloodTypes = List.of(BloodType.values());
        List<Sex> sexes = List.of(Sex.values());
        List<Location> locations = this.locationService.listAll();
        List<Institution> institutions = this.institutionService.listAll();

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("roles", roles);
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("sexes", sexes);
        model.addAttribute("locations", locations);
        model.addAttribute("institutions", institutions);

        return "register";
    }

    @PostMapping("/add")
    public String register(@RequestParam(required = false) Roles role,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                           @RequestParam Sex sex,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String embg,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword
                           ) {
        try {
            this.userService.register(role, name, surname, birthday, sex, email, phone, embg, password, repeatedPassword);
            return "redirect:/login";
        } catch (RuntimeException ex) {
            // Redirect to the register page with an error message
            return "redirect:/register?error=" + ex.getMessage();
        }
    }

}
