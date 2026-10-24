package MessTokenWeb;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByDayOfWeek(String dayOfWeek);
    Optional<MenuItem> findByDayOfWeekAndMealType(String dayOfWeek, String mealType);
}