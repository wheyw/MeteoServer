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
    public ImageView im;
    public TextField fsity;
    public Label name;
    public Label temp;
    public String sity;
    public String response;
    public String request;
    public String IP;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        im.setImage(new Image("image.png"));
        getIP();
        name.setText(request);
        temp.setText(response);
    }

    public void clOk(ActionEvent actionEvent) {
        sity = fsity.getText();
        init();
        name.setText(request);
        temp.setText(response);
    }

    public void init() {

        try (ClServ module = new ClServ(IP, 2654)) {
            System.out.println("Connected to server");
            request = sity;
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
        IP =  myIP.getHostAddress();
        return IP;
    }
}
