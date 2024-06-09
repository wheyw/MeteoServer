import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.meteoServer.services.WeatherData;
import org.meteoServer.services.WeatherService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField cityInput;

    @FXML
    private Label cityLabel;

    @FXML
    private Button getWeatherButton;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label temperatureLabel;

    public ImageView imageView;

    public String city;
    public String response;
    public String request;
    public String ipAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image("src/main/resources/com.example.meteoserver/images/image.png"));
        getIPAddresss();
        cityInput.setText(request);
        temperatureLabel.setText(response);

        getWeatherButton.setOnMouseClicked(this::handlerGetWeatherButton);
    }

    private void handlerGetWeatherButton(MouseEvent event) {
        String city = cityInput.getText();
        try {
            WeatherData weatherData = WeatherService.getWeather(city);
            cityInput.setText("City: "+city);
            temperatureLabel.setText("Temperature: " + weatherData.getTemperature() + "°C");
            humidityLabel.setText("Humidity: " + weatherData.getHumidity() + "%");
        } catch (IOException ioException) {
            temperatureLabel.setText("Error fetching data");
            humidityLabel.setText("");
        }
    }
    public void onOkButtonClicked(ActionEvent actionEvent) {
        city = cityInput.getText();
        init();
        cityLabel.setText(request);
        temperatureLabel.setText(response);
    }

    public void init() {
        try (ClServ module = new ClServ(ipAddress, 2654)) {
            System.out.println("Подключен к серверу");
            request = city;
            module.writeLine(request);
            request = module.readLine();
            response = module.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getIPAddresss() {
        InetAddress myIP = null;
        try {
            myIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Я в игре");
            System.out.println("Ошибка доступа ->" + e);
        }
        ipAddress = myIP.getHostAddress();
        return ipAddress;
    }
}