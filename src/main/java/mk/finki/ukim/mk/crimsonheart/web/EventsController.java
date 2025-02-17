package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.*;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.service.LocationService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private DonationEventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public String getEventsPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) DonationType donationType,
                                @RequestParam(required = false) CityEnum city,
                                Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<DonationEvent> events = new ArrayList<>();
        if (name != null && !name.isEmpty() || donationType != null || city != null) {
            events = this.eventService.filterEvents(name, donationType, city);
        } else
            events = this.eventService.listAll();

        List<CityEnum> cities = List.of(CityEnum.values());
        List<DonationType> types = List.of(DonationType.values());

        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        model.addAttribute("cities", cities);
        model.addAttribute("types", types);
        return "events.html";
    }

    @GetMapping("/viewEvent/{eventId}")
    public String getEventView(@PathVariable(required = true) Long eventId,
                              Model model){
        DonationEvent event = this.eventService.findById(eventId);

        model.addAttribute("event", event);
        return "eventView";
    };

    @GetMapping("/add-form")
    public String getAddEventPage(Model model) {
        List<Location> locations = this.locationService.listAll();
        List<DonationType> donationTypes = List.of(DonationType.values());
        List<Institution> institutions = this.institutionService.listAll();
        List<Users> managers = this.usersService.findByRole(Roles.MANAGER);

        model.addAttribute("locations", locations);
        model.addAttribute("donationTypes", donationTypes);
        model.addAttribute("institutions", institutions);
        model.addAttribute("managers", managers);

        return "add-event";
    }


    @PostMapping("/add")
    public String saveEvent(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam DonationType donationType,
                            @RequestParam Long location,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfEvent,
                            @RequestParam String timeOfEvent,
                            @RequestParam Long institution,
                            @RequestParam Long user){
        if (id != null) {
            this.eventService.updateEvent(id, name, description, donationType, location, dateOfEvent, timeOfEvent, institution, user);
        }else
            this.eventService.createEvent(name, description, donationType, location, dateOfEvent, timeOfEvent, institution, user);
        return "redirect:/events";
    }


    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id){
        this.eventService.delete(id);

        return "redirect:/events";
    }

    @GetMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId, Model model) {
        if (this.eventService.findById(eventId) != null) {
            DonationEvent event = this.eventService.findById(eventId);
            List<Location> locations = this.locationService.listAll();
            List<DonationType> donationTypes = List.of(DonationType.values());
            List<Institution> institutions = this.institutionService.listAll();
            List<Users> managers = this.usersService.findByRole(Roles.MANAGER);

            model.addAttribute("locations", locations);
            model.addAttribute("event", event);
            model.addAttribute("donationTypes", donationTypes);
            model.addAttribute("institutions", institutions);
            model.addAttribute("managers", managers);
            return "add-event";
        }
        return "redirect:/events?error=DonationEventNotFound";
    }

}
