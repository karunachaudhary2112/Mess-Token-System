package MessTokenWeb;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String rollNo;
    private int hostelId;
    private int messId;
    private String password;

    private double totalBalance = 0;
    private double monthlyLimit = 0;
    private double monthlyUsed = 0;

    @ElementCollection
    private List<String> history = new ArrayList<>();

    @ElementCollection
    private Set<String> skippedMealsToday = new HashSet<>();

    // JPA ko ek khali (no-argument) constructor chahiye hota hai - mat hatana ise
    public Student() {
    }

    public Student(String name, String rollNo, int hostelId, int messId, String password) {
        this.name = name;
        this.rollNo = rollNo;
        this.hostelId = hostelId;
        this.messId = messId;
        this.password = password;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public int getHostelId() { return hostelId; }
    public int getMessId() { return messId; }
    public String getPassword() { return password; }

    public double getTotalBalance() { return totalBalance; }
    public double getMonthlyLimit() { return monthlyLimit; }
    public double getMonthlyUsed() { return monthlyUsed; }
    public double getMonthlyRemaining() { return monthlyLimit - monthlyUsed; }

    public void setTotalBalance(double totalBalance) { this.totalBalance = totalBalance; }
    public void setMonthlyLimit(double monthlyLimit) { this.monthlyLimit = monthlyLimit; }
    public void setMonthlyUsed(double monthlyUsed) { this.monthlyUsed = monthlyUsed; }

    public List<String> getHistory() { return history; }
    public void addHistory(String entry) { history.add(0, entry); }

    public Set<String> getSkippedMealsToday() { return skippedMealsToday; }
    public void setSkippedMealsToday(Set<String> meals) { this.skippedMealsToday = meals; }
}