package MessTokenWeb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class MealAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int messId;

    private String mealType;

    private LocalDate mealDate;

    private boolean unavailable;

    public MealAvailability() {
    }

    public MealAvailability(int messId, String mealType,
                            LocalDate mealDate, boolean unavailable) {
        this.messId = messId;
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.unavailable = unavailable;
    }

    public int getId() {
        return id;
    }

    public int getMessId() {
        return messId;
    }

    public String getMealType() {
        return mealType;
    }

    public LocalDate getMealDate() {
        return mealDate;
    }

    public boolean isUnavailable() {
        return unavailable;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

}
