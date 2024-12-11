package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("")
    public String getLocationsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Location> locations = this.locationService.listAll();
        model.addAttribute("bodyContent", "locations");
        model.addAttribute("locations", locations);
        return "locations";
    }

    @GetMapping("/add-form")
    public String getAddLocationPage(Model model) {
        List<CityEnum> cities = List.of(CityEnum.values());

        model.addAttribute("cities", cities);

        return "add-location";
    }


    @PostMapping("/add")
    public String saveLocation(@RequestParam Long id,
                               @RequestParam String address,
                               @RequestParam CityEnum city,
                               @RequestParam String state,
                               @RequestParam String zip,
                               @RequestParam String country) {
        if (id != null && id > 0) {
            this.locationService.update(id, address, city, state, zip, country);
        }else
            this.locationService.create(address, city, state, zip, country);
        return "redirect:/locations";
    }


    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable Long id){
        this.locationService.delete(id);
        return "redirect:/events";
    }

    @GetMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId, Model model) {
        if (this.locationService.findById(eventId).isPresent()) {
            Location location = this.locationService.findById(eventId).get();
            List<CityEnum> cities = List.of(CityEnum.values());

            model.addAttribute("location", location);
            model.addAttribute("cities", cities);
            return "add-location";
        }
        return "redirect:/locations?error=LocationNotFound";
    }
}
