import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SController implements Initializable {
    public String request;
    public String response;
    public final Map<String, Integer> map = new HashMap<>();
    private String res;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (ServerSocket server = new ServerSocket(2654)) {
            System.out.println("Server started");

            while (true) {
                try (ClServ module = new ClServ(server)) {
                    handleClient(module);
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleClient(ClServ module) throws IOException {
        request = module.readerLine();

        synchronized (this) {
            if ("1".equals(request)) {
                module.writeLine(res);
            } else {
                if (map.containsKey(request)) {
                    response = map.get(request).toString();
                    module.writeLine(request);
                    module.writeLine(response);
                    System.out.println("Request: " + request);
                    System.out.println("Response: " + response);
                    res = request + response;
                    System.out.println("Result: " + res);
                } else {
                    response = module.readerLine();
                    map.put(request, Integer.valueOf(response));
                    module.writeLine(request);
                    module.writeLine(response);
                    System.out.println("Map: " + map);
                    res = request + response;
                    System.out.println("Result: " + res);
                }
            }
        }
    }
}