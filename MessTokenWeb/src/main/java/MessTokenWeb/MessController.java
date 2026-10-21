package MessTokenWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MessController {

    @Autowired
    private StudentService studentService;

    private static final String STAFF_PASSWORD = "mess123";

    @GetMapping("/mess-login")
    public String showMessLogin() {
        return "mess-login";
    }

    @PostMapping("/mess-login")
    public String processMessLogin(@RequestParam int messId,
                                   @RequestParam String password,
                                   Model model) {
        if (!password.equals(STAFF_PASSWORD)) {
            model.addAttribute("error", "Incorrect staff password.");
            return "mess-login";
        }
        return "redirect:/mess-dashboard?messId=" + messId;
    }

    @GetMapping("/mess-dashboard")
    public String showMessDashboard(@RequestParam int messId, Model model) {
        Map<String, Integer> skipCounts = studentService.getSkipCounts(messId);
        int totalStudents = studentService.countStudentsInMess(messId);

        model.addAttribute("messId", messId);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("skipCounts", skipCounts);
        return "mess-dashboard";
    }
}