package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/institutions")
public class InstitutionController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private InstitutionService institutionService;

    public InstitutionController(LocationService locationService, InstitutionService institutionService) {
        this.locationService = locationService;
        this.institutionService = institutionService;
    }

    @GetMapping("")
    public String getInstitutionsPage(@RequestParam(required = false) String error,
                                      @RequestParam(required = false) InstitutionsType institutionsType,
                                      @RequestParam(required = false) CityEnum city,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String address,
                                      Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = (Users) authentication.getPrincipal();
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null); // No logged-in user
        }

        List<Institution> institutions = new ArrayList<>();

        if (institutionsType != null || city != null || name != null || address != null) {
            institutions = this.institutionService.filterInstitution(name, institutionsType, address, city);
        } else
            institutions = this.institutionService.listAll();

        List<InstitutionsType> types = List.of(InstitutionsType.values());
        List<CityEnum> cities = List.of(CityEnum.values());

        model.addAttribute("bodyContent", "locations");
        model.addAttribute("institutions", institutions);
        model.addAttribute("types", types);
        model.addAttribute("cities", cities);
        return "institutions";
    }

    @GetMapping("/add-form")
    public String getAddInstitutionPage(Model model) {
        List<Location> locations = this.locationService.listAll();
        List<InstitutionsType> types = List.of(InstitutionsType.values());

        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = (Users) authentication.getPrincipal();
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null); // No logged-in user
        }

        model.addAttribute("locations", locations);
        model.addAttribute("types", types);

        return "add-institution";
    }


    @PostMapping("/add")
    public String saveInstitution(@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam InstitutionsType type,
                               @RequestParam Long locationId) {
        if (id != null && id > 0) {
            this.institutionService.update(id, name, email, phone, type, locationId);
        }else
            this.institutionService.create(name, email, phone, type, locationId);
        return "redirect:/institutions";
    }


    @PostMapping("/delete/{id}")
    public String deleteInstitution(@PathVariable Long id){
        this.institutionService.delete(id);
        return "redirect:/institutions";
    }

    @GetMapping("/edit/{institutionId}")
    public String editInstitution(@PathVariable Long institutionId, Model model) {
        if (this.institutionService.findById(institutionId) != null) {
            Institution institution = this.institutionService.findById(institutionId);
            List<Location> locations = this.locationService.listAll();
            List<InstitutionsType> types = List.of(InstitutionsType.values());

            // Retrieve the authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken)) {
                Users user = (Users) authentication.getPrincipal();
                model.addAttribute("user", user);
            } else {
                model.addAttribute("user", null); // No logged-in user
            }

            model.addAttribute("institution", institution);
            model.addAttribute("locations", locations);
            model.addAttribute("types", types);
            return "add-institution";
        }
        return "redirect:/institutions?error=InstitutionNotFound";
    }

}
