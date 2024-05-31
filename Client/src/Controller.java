import ClientServers.ClServ;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ImageView imageView;
    public TextField cityTextField;
    public Label cityNameLabel;
    public Label temperatureLabel;
    public String city;
    public String response;
    public String request;
    public String ipAddress;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image("image.png"));
        getIPAddress();
        cityNameLabel.setText(request);
        temperatureLabel.setText(response);
    }

    public void onOkButtonClicked(ActionEvent actionEvent) {
        city = cityTextField.getText();
        init();
        cityNameLabel.setText(request);
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

    public String getIPAddress() {
        InetAddress myIP = null;
        try {
            myIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Ошибка доступа ->" + e);
        }
        ipAddress = myIP.getHostAddress();
        return ipAddress;
    }
}
