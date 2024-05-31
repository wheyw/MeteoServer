import ClientServers.ClServ;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SController implements Initializable {
    public String request;
    public String response_;
    public Map<String, Integer> map = new HashMap<String, Integer>();
    private String res;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (ServerSocket server = new ServerSocket(2654);) {
            System.out.println("Server started");

            while (true)
                try (ClServ module = new ClServ(server)) {
                    request = module.readerLine();

                    synchronized (this) {
                        if (request.equals("1")) {
                            module.writeLine(res);
                        } else {
                                if (map != null && map.equals(request) ) {
                                    response_ = map.get(request).toString();
                                    module.writeLine(request);
                                    module.writeLine(response_);
                                    System.out.println(request);
                                    System.out.println(response_);
                                    res = request + response_;
                                    System.out.println(res);
                                } else {

                                    response_ = module.readerLine();
                                    map.put(request, Integer.valueOf(response_));
                                    module.writeLine(request);
                                    module.writeLine(response_);
                                    System.out.println(map);
                                    res = request + response_;
                                    System.out.println(res);
                                }
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
