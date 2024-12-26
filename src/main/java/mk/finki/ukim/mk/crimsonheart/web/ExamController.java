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
    private final DonationEventService eventService;
    private final UsersService usersService;

    public ExamController(ExamService examService, DonationEventService eventService, UsersService usersService) {
        this.examService = examService;
        this.eventService = eventService;
        this.usersService = usersService;
    }

    @GetMapping("")
    public String getExamsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<DonationEvent> events = this.eventService.listAll();
        List<Exam> exams = this.examService.listAll();
        List<Users> users = this.usersService.listAll();

        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        model.addAttribute("exams", exams);
        model.addAttribute("users", users);
        return "exams";
    }

    @GetMapping("/add-form")
    public String getAddExamPage(Model model) {

        List<DonationEvent> events = this.eventService.listAll();
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
    public String saveExam(@RequestParam(required = false) Long id,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date performedOn,
                            @RequestParam String bloodPresure,
                            @RequestParam Float hemoglobin,
                            @RequestParam Long donationEvent,
                            @RequestParam Long doctor,
                            @RequestParam Long nurse,
                            @RequestParam Long patient,
                            @RequestParam Boolean successfulExam,
                            @RequestParam String comment){
        if (id != null) {
            this.examService.update(id, performedOn, bloodPresure, hemoglobin, donationEvent, doctor, patient, nurse, successfulExam, comment );
        }else
            this.examService.create(performedOn, bloodPresure, hemoglobin, donationEvent, doctor, patient, nurse, successfulExam, comment );
        return "redirect:/exams";
    }


    @PostMapping("/delete/{id}")
    public String deleteExam(@PathVariable Long id){
        this.examService.deleteById(id);
        return "redirect:/exams";
    }

    @GetMapping("/edit/{examId}")
    public String editExam(@PathVariable Long examId, Model model) {
        if (this.examService.findById(examId) != null) {
            Exam exam = this.examService.findById(examId);
            List<DonationEvent> events = this.eventService.listAll();
            List<DonationType> donationTypes = List.of(DonationType.values());
            List<Users> doctors = this.usersService.findByRole(Roles.DOCTOR);
            List<Users> patients = this.usersService.findByRole(Roles.PATIENT);
            List<Users> nurses = this.usersService.findByRole(Roles.NURSE);

            model.addAttribute("exam", exam);
            model.addAttribute("events", events);
            model.addAttribute("donationTypes", donationTypes);
            model.addAttribute("doctors", doctors);
            model.addAttribute("patients", patients);
            model.addAttribute("nurses", nurses);

            return "add-patient-exam";
        }
        return "redirect:/exams?error=ExamNotFound";
    }

}
