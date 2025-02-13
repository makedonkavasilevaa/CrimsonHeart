package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.service.DonationEventService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<DonationEvent> events = this.eventService.listAll();

        List<DonationEvent> latestEvents = events.stream()
                .sorted((event1, event2) -> event2.getDateOfEvent().compareTo(event1.getDateOfEvent())) // assuming `getDate` gives you a comparable Date object
                .limit(4)
                .collect(Collectors.toList());

        model.addAttribute("bodyContent", "events");
        model.addAttribute("events", events);
        return "homepage";
    }
}
