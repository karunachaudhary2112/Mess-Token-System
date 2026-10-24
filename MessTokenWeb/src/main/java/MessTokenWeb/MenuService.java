package MessTokenWeb;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // Jab app pehli baar start ho aur menu table khali ho, to default weekly menu daal do
    @PostConstruct
    public void seedDefaultMenu() {
        if (menuRepository.count() > 0) return;

        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String[][] breakfast = {
                {"Poha, Tea", "60"}, {"Idli Sambhar", "65"}, {"Paratha, Curd", "70"},
                {"Upma, Tea", "55"}, {"Bread Omelette/Toast, Tea", "65"}, {"Chole Bhature", "75"}, {"Sprouts, Tea", "55"}
        };
        String[][] lunch = {
                {"Dal, Rice, Roti, Sabzi", "90"}, {"Rajma Chawal, Roti", "95"}, {"Kadhi Chawal, Roti", "85"},
                {"Dal, Rice, Mix Veg, Roti", "90"}, {"Chole, Rice, Roti", "95"}, {"Paneer Sabzi, Rice, Roti", "110"}, {"Veg Pulao, Raita", "90"}
        };
        String[][] dinner = {
                {"Dal, Rice, Roti, Sabzi", "85"}, {"Khichdi, Papad", "70"}, {"Paneer, Roti, Rice", "105"},
                {"Dal Fry, Rice, Roti", "85"}, {"Veg Curry, Roti, Rice", "90"}, {"Pulao, Raita", "90"}, {"Sambhar Rice, Roti", "80"}
        };

        for (int i = 0; i < 7; i++) {
            menuRepository.save(new MenuItem(days[i], "Breakfast", breakfast[i][0], Double.parseDouble(breakfast[i][1])));
            menuRepository.save(new MenuItem(days[i], "Lunch", lunch[i][0], Double.parseDouble(lunch[i][1])));
            menuRepository.save(new MenuItem(days[i], "Dinner", dinner[i][0], Double.parseDouble(dinner[i][1])));
        }
    }

    public List<MenuItem> getTodayMenu() {
        return getMenuForDay(getTodayName());
    }

    public List<MenuItem> getMenuForDay(String dayOfWeek) {
        List<MenuItem> items = menuRepository.findByDayOfWeek(
                dayOfWeek.toUpperCase(Locale.ROOT)
        );

        items.sort(Comparator.comparingInt(
                m -> mealOrder(m.getMealType())
        ));

        return items;
    }

    private int mealOrder(String mealType) {
        switch (mealType) {
            case "Breakfast": return 1;
            case "Lunch": return 2;
            case "Dinner": return 3;
            default: return 4;
        }
    }

    public double getMealPrice(String dayOfWeek, String mealType) {
        Optional<MenuItem> item = menuRepository.findByDayOfWeekAndMealType(dayOfWeek, mealType);
        return item.map(MenuItem::getTokenPrice).orElse(0.0);
    }

    public String getTodayName() {
        return LocalDate.now().getDayOfWeek().toString();
    }
}