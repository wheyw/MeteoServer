import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByCityAndDateBetween(String city, LocalDate startDate, LocalDate endDate);
}
