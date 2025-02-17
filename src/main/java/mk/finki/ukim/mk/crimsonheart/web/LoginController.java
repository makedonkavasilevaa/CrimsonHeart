package mk.finki.ukim.mk.crimsonheart.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
    @PostMapping()
    public String login(HttpServletRequest request, Model model) {
        Users user = null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            user = authService.login(username, password);
            request.getSession().setAttribute("user", user);
            // Store userId in session
            request.getSession().setAttribute("userId", user.getId());

            System.out.println("userID: "+request.getSession().getAttribute("userId"));
            System.out.println("user: "+request.getSession().getAttribute("user"));
            // Redirect to the home page
            return "redirect:/events";
        } catch (RuntimeException ex) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }
}
