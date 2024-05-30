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
    public ImageView imageView;
    public TextField cityTextField;
    public Label nameLabel;
    public Label tempLabel;
    public String city;
    public String response;
    public String request;
    public String IP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image("image.png"));
        getLocalIPAddress();
        nameLabel.setText(request);
        tempLabel.setText(response);
    }

    public void onOkButtonClicked(ActionEvent actionEvent) {
        city = cityTextField.getText();
        sendRequestToServer();
        nameLabel.setText(request);
        tempLabel.setText(response);
    }

    private void sendRequestToServer() {
        try (ClServ module = new ClServ(IP, 2654)) {
            System.out.println("Connected to server");
            request = city;
            module.writeLine(request);
            request = module.readerLine();
            response = module.readerLine();
            System.out.println("Response from server: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLocalIPAddress() {
        InetAddress myIP = null;
        try {
            myIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Error accessing IP address ->" + e);
        }
        IP = (myIP != null) ? myIP.getHostAddress() : "Unknown";
    }
}