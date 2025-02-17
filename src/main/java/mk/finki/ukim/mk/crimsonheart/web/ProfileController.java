package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ExamService examService;

    @GetMapping("")
    public String getProfilePage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String patientEmbg,
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

        List<Exam> exams = this.examService.findByPatientEmbg(patientEmbg);

        model.addAttribute("bodyContent", "events");
        model.addAttribute("exams", exams);
        return "profile";
    }



}
