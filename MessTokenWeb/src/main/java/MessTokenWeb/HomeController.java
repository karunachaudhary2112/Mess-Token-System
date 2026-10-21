package MessTokenWeb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";   // ye "home.html" file ko dhoondega templates folder mein
    }
}