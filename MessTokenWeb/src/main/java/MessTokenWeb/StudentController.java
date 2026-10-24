package MessTokenWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String name,
                                @RequestParam String rollNo,
                                @RequestParam int hostelId,
                                @RequestParam int messId,
                                @RequestParam String password,
                                Model model) {

        String result = studentService.register(name, rollNo, hostelId, messId, password);

        if (result.equals("success")) {
            model.addAttribute("message", "Registration successful! Please log in.");
            return "login";
        } else {
            model.addAttribute("error", result);
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String rollNo,
                               @RequestParam String password,
                               Model model) {

        Student student = studentService.login(rollNo, password);

        if (student != null) {
            return "redirect:/dashboard?id=" + student.getId();
        } else {
            model.addAttribute("error", "Invalid Roll No or Password.");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam int id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", student);
        return "dashboard";
    }

    @PostMapping("/dashboard/payfees")
    public String payFees(@RequestParam int id, @RequestParam double yearlyFee) {
        studentService.payFees(id, yearlyFee);
        return "redirect:/dashboard?id=" + id;
    }

    @GetMapping("/scan")
    public String showScanPage(@RequestParam int id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) return "redirect:/login";
        model.addAttribute("student", student);
        model.addAttribute("breakfastPrice",
                menuService.getMealPrice(menuService.getTodayName(), "Breakfast"));
        model.addAttribute("lunchPrice",
                menuService.getMealPrice(menuService.getTodayName(), "Lunch"));
        model.addAttribute("dinnerPrice",
                menuService.getMealPrice(menuService.getTodayName(), "Dinner"));
        return "scan";
    }

    @PostMapping("/scan")
    public String processScan(@RequestParam int id,
                              @RequestParam int messId,
                              @RequestParam double amount,
                              @RequestParam String mealType,
                              @RequestParam String password,
                              Model model) {

        double actualAmount = menuService.getMealPrice(
                menuService.getTodayName(), mealType);

        String result;

        if (actualAmount <= 0) {
            result = "Failed: Invalid meal or price not found.";
        } else {
            result = studentService.debit(
                    id, messId, actualAmount, mealType, password);
        }
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("result", result);
        return "scan";
    }

    @GetMapping("/history")
    public String showHistory(@RequestParam int id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) return "redirect:/login";
        model.addAttribute("student", student);
        return "history";
    }

    @GetMapping("/preorder")
    public String showPreorderPage(@RequestParam int id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) return "redirect:/login";
        model.addAttribute("student", student);
        return "preorder";
    }

    @PostMapping("/preorder")
    public String processPreorder(@RequestParam int id,
                                  @RequestParam(required = false) java.util.List<String> skipMeals) {
        java.util.Set<String> skipped = new java.util.HashSet<>();
        if (skipMeals != null) {
            skipped.addAll(skipMeals);
        }
        studentService.setPreorder(id, skipped);
        return "redirect:/dashboard?id=" + id;
    }
    @GetMapping("/special-orders")
    public String specialOrders(@RequestParam int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "special-orders";
    }

    @GetMapping("/diet-orders")
    public String dietOrders(@RequestParam int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "diet-orders";
    }

    @PostMapping("/special-orders")
    public String placeSpecialOrder(@RequestParam int id,
                                    @RequestParam String food,
                                    Model model) {
        String result = studentService.placeOrder(id, food, "special");
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("result", result);
        return "special-orders";
    }

    @PostMapping("/diet-orders")
    public String placeDietOrder(@RequestParam int id,
                                 @RequestParam String food,
                                 Model model) {
        String result = studentService.placeOrder(id, food, "diet");
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("result", result);
        return "diet-orders";
    }

}