package mk.finki.ukim.mk.crimsonheart.web;

import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.service.AuthService;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UsersService userService;

    public RegisterController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        return "register";
    }

    //TODO: register a user
//    @PostMapping
//    public String register(@RequestParam String username,
//                           @RequestParam String password,
//                           @RequestParam String repeatedPassword,
//                           @RequestParam String name,
//                           @RequestParam String surname,
//                           @RequestParam Roles role
//    ) {
//        try {
//            this.userService.register(username, password, repeatedPassword, name, surname);
//            return "redirect:/login";
//        } catch (RuntimeException ex) {
//            // Redirect to the register page with an error message
//            return "redirect:/register?error=" + ex.getMessage();
//        }
//    }

}
