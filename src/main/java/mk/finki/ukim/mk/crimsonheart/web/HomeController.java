package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
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
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DonationEventService eventService;

    @GetMapping("")
    public String getHomePage(@RequestParam(required = false) String error, Model model){
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

        List<DonationEvent> events = this.eventService.listAll();
        List<DonationEvent> latestEvents = events.stream()
                .sorted((event1, event2) -> event2.getDateOfEvent().compareTo(event1.getDateOfEvent())) // assuming `getDate` gives you a comparable Date object
                .limit(4)
                .collect(Collectors.toList());

        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", latestEvents);
        return "homepage";
    }
}
