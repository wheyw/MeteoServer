import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class NewController {

    @Autowired
    private WeatherRepository weatherRepository; // Предполагается, что есть репозиторий для работы с данными о погоде

    @GetMapping("/averageTemperature")
    public double getAverageTemperature(
            @RequestParam("city") String city,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        // Предполагается, что в репозитории есть метод для получения данных о погоде по городу и датам
        // Этот метод должен вернуть список данных о погоде за указанный период
        List<WeatherData> weatherDataList = weatherRepository.findByCityAndDateBetween(city, startDate, endDate);

        // Вычисляем среднюю температуру по данным о погоде за указанный период
        double sumTemperature = 0;
        for (WeatherData weatherData : weatherDataList) {
            sumTemperature += weatherData.getTemperature();
        }
        double averageTemperature = sumTemperature / weatherDataList.size();

        return averageTemperature;
    }
}
