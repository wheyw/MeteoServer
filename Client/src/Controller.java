import ClientServers.ClServ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView im;

    @FXML
    private TextField fsity;

    @FXML
    private Label name;

    @FXML
    private Label temp;

    private String sity;
    private String response;
    private String request;
    private String IP;
    private Map<String, String> history = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        im.setImage(new Image("image.png"));
        getIP();
        name.setText("");
        temp.setText("");
    }

    @FXML
    public void clOk(ActionEvent actionEvent) {
        sity = fsity.getText().trim();
        if (!sity.isEmpty()) {
            request = sity;
            response = getResponseFromHistory(request);
            if (response == null) {
                init();
                saveResponseToHistory(request, response);
            }
            name.setText(request);
            temp.setText(response);
        }
    }

    public void init() {
        try (ClServ module = new ClServ(IP, 2654)) {
            System.out.println("Connected to server");
            module.writeLine(request);
            request = module.readerLine();
            response = module.readerLine();
            System.out.println("" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIP() {
        InetAddress myIP = null;
        try {
            myIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(" ошибка доступа ->" + e);
        }
        IP = myIP.getHostAddress();
        return IP;
    }

    private String getResponseFromHistory(String request) {
        return history.get(request);
    }

    private void saveResponseToHistory(String request, String response) {
        history.put(request, response);
    }
}
