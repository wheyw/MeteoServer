import ClientServers.ClServ;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SController implements Initializable {
    public String request;
    public String response;
    private final HashMap<String, Integer> map = new HashMap<>();
    private String result;
    private final Object lock = new Object();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (ServerSocket server = new ServerSocket(2654)) {
            System.out.println("Сервер запущен");

            while (true) {
                try (ClServ module = new ClServ(server)) {
                    request = module.readerLine();
                    result = processRequest(request, module);
                    System.out.println(result);
                } catch (IOException e) {
                    System.err.println("Ошибка при обработке запроса клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    private String processRequest(String request, ClServ module) {
        String result = "";
        synchronized (lock) {
            if (request.equals("1")) {
                module.writeLine(result);
            } else {
                Integer value = getMapValue(request);
                if (value != null) {
                    response = value.toString();
                    result = request + " " + response;
                } else {
                    response = module.readerLine();
                    map.put(request, Integer.valueOf(response));
                    result = request + " " + response;
                }
            }
        }
        return result;
    }

    public Integer getMapValue(String key) {
        synchronized (lock) {
            return map.get(key);
        }
    }
}
