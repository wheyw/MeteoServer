import ClientServers.ClServ;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // UI Elements
    public ImageView imageView;
    public TextField cityTextField;
    public Label nameLabel;
    public Label temperatureLabel;

    // Instance Variables
    private String city;
    private String response;
    private String request;
    private String ipAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default image and fetch IP address on initialization
        imageView.setImage(new Image("image.png"));
        ipAddress = getLocalIPAddress();
        nameLabel.setText(request);
        temperatureLabel.setText(response);
    }

    // Event handler for OK button click
    public void onOkButtonClick(ActionEvent actionEvent) {
        city = cityTextField.getText();
        fetchWeatherData();
        nameLabel.setText(request);
        temperatureLabel.setText(response);
    }

    // Initialize connection to server and fetch weather data
    private void fetchWeatherData() {
        try (ClServ module = new ClServ(ipAddress, 2654)) {
            System.out.println("Connected to server");
            request = city;
            module.writeLine(request);
            request = module.readLine();
            response = module.readLine();
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the local IP address
    private String getLocalIPAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Access error -> " + e);
            return "Unknown IP";
        }
    }
}