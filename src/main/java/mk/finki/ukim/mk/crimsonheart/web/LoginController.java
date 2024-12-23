package mk.finki.ukim.mk.crimsonheart.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/login")
    @GetMapping
    public String getLoginPage() {
        // Return the name of the Thymeleaf template that will be used to render the login page
        return "login";
    }
    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        Users user = null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            user = authService.login(username, password);
            request.getSession().setAttribute("user", user);
            // Redirect to the home page
            return "redirect:/events";
        } catch (RuntimeException ex) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }
}
