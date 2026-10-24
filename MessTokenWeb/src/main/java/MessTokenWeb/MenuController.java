package MessTokenWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/menu")
    public String showMenu(
            @RequestParam int id,
            @RequestParam(required = false) String day,
            Model model) {

        Student student = studentService.findById(id);

        if (student == null) {
            return "redirect:/login";
        }

        String selectedDay = day == null || day.isBlank()
                ? menuService.getTodayName()
                : day.toUpperCase(java.util.Locale.ROOT);

        model.addAttribute("student", student);
        model.addAttribute("todayName", menuService.getTodayName());
        model.addAttribute("selectedDay", selectedDay);
        model.addAttribute("menuItems", menuService.getMenuForDay(selectedDay));

        return "menu";
    }
}