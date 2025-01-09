package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final DonationEventService eventService;

    public HomeController(DonationEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public String getHomePage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<DonationEvent> events = this.eventService.listAll();
        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        return "homepage";
    }
}
