package MessTokenWeb;
import java.time.LocalDate;
import java.time.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Arrays;
import java.util.Collections;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MealAvailabilityRepository availabilityRepository;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public String register(String name, String rollNo, int hostelId,
                           int messId, String password) {

        Optional<Student> existing = studentRepository.findByRollNo(rollNo);

        if (existing.isPresent()) {
            return "Failed: This Roll No is already registered.";
        }

        Student newStudent =
                new Student(name, rollNo, hostelId, messId, password);

        studentRepository.save(newStudent);
        return "success";
    }

    public Student login(String rollNo, String password) {
        Optional<Student> found = studentRepository.findByRollNo(rollNo);

        if (found.isPresent()
                && found.get().getPassword().equals(password)) {
            return found.get();
        }

        return null;
    }

    public Student findById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public String payFees(int id, double yearlyFee) {
        Student s = findById(id);

        if (s == null) {
            return "Student not found.";
        }

        if (yearlyFee <= 0) {
            return "Failed: Fee must be greater than zero.";
        }

        s.setTotalBalance(s.getTotalBalance() + yearlyFee);
        s.setMonthlyLimit(yearlyFee / 12.0);

        s.addHistory(
                LocalDateTime.now().format(FORMAT)
                        + " | CREDIT | " + yearlyFee
                        + " tokens | yearly mess fee"
        );

        studentRepository.save(s);
        return "Fees paid successfully.";
    }

    public String debit(int id, int messIdAttempted, double amount,
                        String mealType, String passwordAttempt) {

        Student s = findById(id);

        if (s == null) {
            return "Student not found.";
        }

        if (!s.getPassword().equals(passwordAttempt)) {
            return "Failed: Incorrect password.";
        }

        if (s.getMessId() != messIdAttempted) {
            return "Failed: You are assigned to Mess ID "
                    + s.getMessId() + ", not " + messIdAttempted + ".";
        }

        if (!mealType.equals("Breakfast")
                && !mealType.equals("Lunch")
                && !mealType.equals("Dinner")) {
            return "Failed: Invalid meal type.";
        }

        if (!isMealAvailable(messIdAttempted, mealType)) {
            return "Failed: This meal is unavailable today.";
        }

        if (amount <= 0) {
            return "Failed: Amount must be greater than zero.";
        }

        if (amount > s.getTotalBalance()) {
            return "Failed: Insufficient total tokens.";
        }

        if (s.getMonthlyUsed() + amount > s.getMonthlyLimit()) {
            return "Failed: Monthly limit exceeded. Used "
                    + s.getMonthlyUsed() + " / "
                    + s.getMonthlyLimit() + " tokens";
        }

        s.setTotalBalance(s.getTotalBalance() - amount);
        s.setMonthlyUsed(s.getMonthlyUsed() + amount);

        s.addHistory(
                LocalDateTime.now().format(FORMAT)
                        + " | DEBIT | " + amount
                        + " tokens | " + mealType
        );

        studentRepository.save(s);

        return "Payment successful: " + amount
                + " tokens (" + mealType + ")";
    }

    public String setPreorder(int id, Set<String> skippedMeals) {
        Student s = findById(id);

        if (s == null) {
            return "Student not found.";
        }

        s.setSkippedMealsToday(skippedMeals);
        studentRepository.save(s);

        return "success";
    }

    public Map<String, Integer> getSkipCounts(int messId) {
        Map<String, Integer> counts = new HashMap<>();

        counts.put("Breakfast", 0);
        counts.put("Lunch", 0);
        counts.put("Dinner", 0);

        List<Student> messStudents =
                studentRepository.findByMessId(messId);

        for (Student s : messStudents) {
            for (String meal : s.getSkippedMealsToday()) {
                counts.put(
                        meal,
                        counts.getOrDefault(meal, 0) + 1
                );
            }
        }

        return counts;
    }

    public int countStudentsInMess(int messId) {
        return studentRepository.findByMessId(messId).size();
    }

    public boolean isMealAvailable(int messId, String mealType) {
        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();

        // Saturday and Sunday: all meals remain eligible.
        if (day == DayOfWeek.SATURDAY
                || day == DayOfWeek.SUNDAY) {
            return true;
        }

        // Monday to Friday: check the mess's availability record.
        return availabilityRepository
                .findByMessIdAndMealTypeAndMealDate(
                        messId, mealType, today
                )
                .map(record -> !record.isUnavailable())
                .orElse(true);
    }
    private final Map<String, Integer> orderStock = new HashMap<>();

    {
        orderStock.put("Maggi", 20);
        orderStock.put("Noodles", 15);
        orderStock.put("Pizza", 10);
        orderStock.put("Fried Rice", 15);
        orderStock.put("Manchurian", 15);
        orderStock.put("Momos", 20);
        orderStock.put("French Fries", 20);

        orderStock.put("Sprouts Salad", 20);
        orderStock.put("Fruit Bowl", 20);
        orderStock.put("Oats", 20);
        orderStock.put("Boiled Eggs", 20);
        orderStock.put("Paneer Salad", 15);

    }

    private final Map<String, Double> orderPrices = new HashMap<>();

    {
        orderPrices.put("Maggi", 100.0);
        orderPrices.put("Noodles", 160.0);
        orderPrices.put("Pizza", 180.0);
        orderPrices.put("Fried Rice", 140.0);
        orderPrices.put("Manchurian", 120.0);
        orderPrices.put("Momos", 100.0);
        orderPrices.put("French Fries", 90.0);

        orderPrices.put("Sprouts Salad", 65.0);
        orderPrices.put("Fruit Bowl", 90.0);
        orderPrices.put("Oats", 75.0);
        orderPrices.put("Boiled Eggs", 50.0);
        orderPrices.put("Paneer Salad", 100.0);

    }

    public synchronized String placeOrder(int id, String food, String category) {
        Student s = findById(id);

        if (s == null) return "Failed: Student not found.";

        boolean special = category.equals("special");
        boolean validCategory = special
                ? Arrays.asList("Maggi", "Noodles", "Pizza", "Fried Rice",
                "Manchurian", "Momos", "French Fries").contains(food)
                : category.equals("diet") && Arrays.asList(
                "Sprouts Salad", "Fruit Bowl", "Oats",
                "Boiled Eggs", "Paneer Salad").contains(food);

        if (!validCategory) return "Failed: Invalid order item.";
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        boolean alreadyAte = s.getHistory().stream().anyMatch(entry ->
                entry.contains(" | DEBIT | ")
                        && entry.contains(" tokens | ")
                        && entry.contains(todayDate)
                        && (entry.contains("Breakfast")
                        || entry.contains("Lunch")
                        || entry.contains("Dinner"))
        );

        if (alreadyAte) {
            return "Failed: You have already eaten a meal today. You cannot place special or diet orders.";
        }


        int messId = s.getMessId();

        if (orderStock.getOrDefault(food, 0) <= 0) {
            return "Failed: This item is out of stock.";
        }

        double price = orderPrices.get(food);

        if (s.getTotalBalance() < price) {
            return "Failed: Insufficient balance.";
        }

        if (s.getMonthlyUsed() + price > s.getMonthlyLimit()) {
            return "Failed: Monthly limit exceeded.";
        }

        s.setTotalBalance(s.getTotalBalance() - price);
        s.setMonthlyUsed(s.getMonthlyUsed() + price);
        s.addHistory(LocalDateTime.now().format(FORMAT)
                + " | DEBIT | " + price + " tokens | " + food + " order");

        orderStock.put(food, orderStock.get(food) - 1);
        studentRepository.save(s);

        return "Order successful: " + food + " | Paid " + price
                + " tokens | Remaining stock: " + orderStock.get(food);

    }

    public synchronized int getOrderStock(String food) {
        return orderStock.getOrDefault(food, 0);
    }

}