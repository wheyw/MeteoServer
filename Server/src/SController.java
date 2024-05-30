import ClientServers.ClServ;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SController implements Initializable {
    private String request;
    private String response;
    private final Map<String, Integer> map = new HashMap<>();
    private String result;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startServer();
    }

    // Метод для старта сервера
    private void startServer() {
        try (ServerSocket server = new ServerSocket(2654)) {
            System.out.println("Server started");

            while (true) {
                try (ClServ module = new ClServ(server)) {
                    handleClient(module);
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для обработки клиента
    private void handleClient(ClServ module) throws IOException {
        request = module.readLine();

        synchronized (this) {
            if ("1".equals(request)) {
                module.writeLine(result);
            } else {
                processRequest(module);
            }
        }
    }

    // Метод для обработки запроса
    private void processRequest(ClServ module) throws IOException {
        if (map.containsKey(request)) {
            response = map.get(request).toString();
            module.writeLine(request);
            module.writeLine(response);
            System.out.println("Request: " + request);
            System.out.println("Response: " + response);
            result = request + response;
            System.out.println("Result: " + result);
        } else {
            response = module.readLine();
            map.put(request, Integer.valueOf(response));
            module.writeLine(request);
            module.writeLine(response);
            System.out.println("Map: " + map);
            result = request + response;
            System.out.println("Result: " + result);
        }
    }
}