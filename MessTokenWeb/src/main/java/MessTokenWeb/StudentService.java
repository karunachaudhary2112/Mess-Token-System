package MessTokenWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public String register(String name, String rollNo, int hostelId, int messId, String password) {
        Optional<Student> existing = studentRepository.findByRollNo(rollNo);
        if (existing.isPresent()) {
            return "Failed: This Roll No is already registered.";
        }
        Student newStudent = new Student(name, rollNo, hostelId, messId, password);
        studentRepository.save(newStudent);
        return "success";
    }

    public Student login(String rollNo, String password) {
        Optional<Student> found = studentRepository.findByRollNo(rollNo);
        if (found.isPresent() && found.get().getPassword().equals(password)) {
            return found.get();
        }
        return null;
    }

    public Student findById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public String payFees(int id, double yearlyFee) {
        Student s = findById(id);
        if (s == null) return "Student not found.";

        s.setTotalBalance(s.getTotalBalance() + yearlyFee);
        s.setMonthlyLimit(yearlyFee / 12.0);
        s.addHistory(LocalDateTime.now().format(FORMAT) + " | CREDIT | " + yearlyFee + " tokens | yearly mess fee");
        studentRepository.save(s);
        return "Fees paid successfully.";
    }

    public String debit(int id, int messIdAttempted, double amount, String mealType, String passwordAttempt) {
        Student s = findById(id);
        if (s == null) return "Student not found.";

        if (!s.getPassword().equals(passwordAttempt)) {
            return "Failed: Incorrect password.";
        }
        if (s.getMessId() != messIdAttempted) {
            return "Failed: You are assigned to Mess ID " + s.getMessId() + ", not " + messIdAttempted + ".";
        }
        if (amount > s.getTotalBalance()) {
            return "Failed: Insufficient total tokens.";
        }
        if (s.getMonthlyUsed() + amount > s.getMonthlyLimit()) {
            return "Failed: Monthly limit exceeded. Used " + s.getMonthlyUsed() + " / " + s.getMonthlyLimit() + " tokens";
        }

        s.setTotalBalance(s.getTotalBalance() - amount);
        s.setMonthlyUsed(s.getMonthlyUsed() + amount);
        s.addHistory(LocalDateTime.now().format(FORMAT) + " | DEBIT | " + amount + " tokens | " + mealType);
        studentRepository.save(s);
        return "Payment successful: " + amount + " tokens (" + mealType + ")";
    }

    public String setPreorder(int id, Set<String> skippedMeals) {
        Student s = findById(id);
        if (s == null) return "Student not found.";
        s.setSkippedMealsToday(skippedMeals);
        studentRepository.save(s);
        return "success";
    }

    public Map<String, Integer> getSkipCounts(int messId) {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("Breakfast", 0);
        counts.put("Lunch", 0);
        counts.put("Dinner", 0);

        List<Student> messStudents = studentRepository.findByMessId(messId);
        for (Student s : messStudents) {
            for (String meal : s.getSkippedMealsToday()) {
                counts.put(meal, counts.getOrDefault(meal, 0) + 1);
            }
        }
        return counts;
    }

    public int countStudentsInMess(int messId) {
        return studentRepository.findByMessId(messId).size();
    }
}