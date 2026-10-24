package MessTokenWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class MessController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MealAvailabilityRepository availabilityRepository;

    private static final String STAFF_PASSWORD = "mess123";

    @GetMapping("/mess-login")
    public String showMessLogin() {
        return "mess-login";
    }

    @PostMapping("/mess-login")
    public String processMessLogin(
            @RequestParam int messId,
            @RequestParam String password,
            Model model) {

        if (!password.equals(STAFF_PASSWORD)) {
            model.addAttribute("error", "Incorrect staff password.");
            return "mess-login";
        }

        return "redirect:/mess-dashboard?messId=" + messId;
    }

    @GetMapping("/mess-dashboard")
    public String showMessDashboard(
            @RequestParam int messId,
            Model model) {

        Map<String, Integer> skipCounts =
                studentService.getSkipCounts(messId);

        int totalStudents =
                studentService.countStudentsInMess(messId);

        model.addAttribute("messId", messId);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("skipCounts", skipCounts);
        model.addAttribute("today", LocalDate.now());

        model.addAttribute("breakfastUnavailable",
                isUnavailable(messId, "Breakfast"));

        model.addAttribute("lunchUnavailable",
                isUnavailable(messId, "Lunch"));

        model.addAttribute("dinnerUnavailable",
                isUnavailable(messId, "Dinner"));

        return "mess-dashboard";
    }

    @PostMapping("/mess-dashboard/availability")
    public String updateAvailability(
            @RequestParam int messId,
            @RequestParam String mealType,
            @RequestParam boolean unavailable,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        if (!STAFF_PASSWORD.equals(password)) {
            redirectAttributes.addFlashAttribute(
                    "error", "Invalid staff password.");
            return "redirect:/mess-login";
        }

        if (!mealType.equals("Breakfast")
                && !mealType.equals("Lunch")
                && !mealType.equals("Dinner")) {

            redirectAttributes.addFlashAttribute(
                    "error", "Invalid meal type.");

            return "redirect:/mess-dashboard?messId=" + messId;
        }

        LocalDate today = LocalDate.now();

        MealAvailability record =
                availabilityRepository
                        .findByMessIdAndMealTypeAndMealDate(
                                messId, mealType, today)
                        .orElseGet(() ->
                                new MealAvailability(
                                        messId, mealType, today, unavailable));

        record.setUnavailable(unavailable);
        availabilityRepository.save(record);

        redirectAttributes.addFlashAttribute(
                "message", mealType + " availability updated.");

        return "redirect:/mess-dashboard?messId=" + messId;
    }

    private boolean isUnavailable(int messId, String mealType) {
        return availabilityRepository
                .findByMessIdAndMealTypeAndMealDate(
                        messId, mealType, LocalDate.now())
                .map(MealAvailability::isUnavailable)
                .orElse(false);
    }

}
