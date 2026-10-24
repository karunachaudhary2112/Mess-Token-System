package MessTokenWeb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MealAvailabilityRepository
        extends JpaRepository<MealAvailability, Integer> {

    Optional<MealAvailability> findByMessIdAndMealTypeAndMealDate(
            int messId,
            String mealType,
            LocalDate mealDate
    );

}
