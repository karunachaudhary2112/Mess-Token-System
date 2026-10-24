package MessTokenWeb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dayOfWeek;      // e.g. "MONDAY"
    private String mealType;       // "Breakfast", "Lunch", "Dinner"
    private String itemsDescription; // e.g. "Poha, Tea, Banana"
    private double tokenPrice;

    public MenuItem() {}

    public MenuItem(String dayOfWeek, String mealType, String itemsDescription, double tokenPrice) {
        this.dayOfWeek = dayOfWeek;
        this.mealType = mealType;
        this.itemsDescription = itemsDescription;
        this.tokenPrice = tokenPrice;
    }

    public int getId() { return id; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getMealType() { return mealType; }
    public String getItemsDescription() { return itemsDescription; }
    public double getTokenPrice() { return tokenPrice; }
}