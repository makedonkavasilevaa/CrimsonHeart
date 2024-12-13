package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.*;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;
    private final DonationEventService donationEventService;
    private final UsersService usersService;

    public ExamController(ExamService examService, DonationEventService donationEventService, UsersService usersService) {
        this.examService = examService;
        this.donationEventService = donationEventService;
        this.usersService = usersService;
    }

    @GetMapping("")
    public String getExamsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<DonationEvent> events = this.donationEventService.listAll();
        List<Exam> exams = this.examService.listAll();
        List<Users> users = this.usersService.listAll();

        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        model.addAttribute("exams", exams);
        model.addAttribute("users", users);
        return "exams";
    }

    @GetMapping("/add-form")
    public String getAddEventPage(Model model) {

        List<DonationEvent> events = this.donationEventService.listAll();
        List<Users> doctors = this.usersService.findByRole(Roles.DOCTOR);
        List<Users> nurses = this.usersService.findByRole(Roles.NURSE);
        List<Users> patients = this.usersService.findByRole(Roles.PATIENT);

        model.addAttribute("events", events);
        model.addAttribute("doctors", doctors);
        model.addAttribute("nurses", nurses);
        model.addAttribute("patients", patients);

        return "add-patient-exam";
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
