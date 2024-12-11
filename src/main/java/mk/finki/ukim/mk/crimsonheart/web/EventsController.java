package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    private final DonationEventService eventService;
    private final LocationService locationService;
    private final InstitutionService institutionService;
    private final UsersService usersService;

    public EventsController(DonationEventService donationEventService, LocationService locationService, InstitutionService institutionService, UsersService usersService) {
        this.eventService = donationEventService;
        this.locationService = locationService;
        this.institutionService = institutionService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String getEventsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<DonationEvent> events = this.eventService.listAll();
        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/add-form")
    public String getAddEventPage(Model model) {
        List<DonationEvent> events = this.eventService.listAll();
        List<Location> locations = this.locationService.listAll();
        List<DonationType> donationTypes = List.of(DonationType.values());
        List<Institution> institutions = this.institutionService.listAll();
        List<Users> users = this.usersService.listAll();

        model.addAttribute("locations", locations);
        model.addAttribute("events", events);
        model.addAttribute("donationTypes", donationTypes);
        model.addAttribute("institutions", institutions);
        model.addAttribute("users", users);

        return "add-event";
    }


    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam DonationType donationType,
                            @RequestParam Long location,
                            @RequestParam Date dateAndTime,
                            @RequestParam Long institution,
                            @RequestParam Long user){
        this.eventService.save(name, description, donationType, location, dateAndTime, institution, user);
        return "redirect:/events";
    }


    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id){
        this.eventService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId, Model model) {
        if (this.eventService.findById(eventId).isPresent()) {
            DonationEvent event = this.eventService.findById(eventId).orElseThrow();
            List<Location> locations = this.locationService.listAll();
            List<DonationType> donationTypes = List.of(DonationType.values());
            List<Institution> institutions = this.institutionService.listAll();
            List<Users> users = this.usersService.listAll();

            model.addAttribute("locations", locations);
            model.addAttribute("event", event);
            model.addAttribute("donationTypes", donationTypes);
            model.addAttribute("institutions", institutions);
            model.addAttribute("users", users);
            return "add-event";
        }
        return "redirect:/events?error=DonationEventNotFound";
    }

}
