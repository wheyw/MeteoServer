import ClientServers.ClientServer;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    // Переменные для хранения запроса и ответа
    private String clientRequest;
    private String serverResponse;

    // Карта для хранения запросов и их соответствующих ответов
    private final Map<String, Integer> requestDataMap = new HashMap<>();

    // Переменная для хранения комбинированного ответа
    private String combinedResponse;

    // Метод инициализации, вызывается при запуске контроллера
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (ServerSocket serverSocket = new ServerSocket(2654)) {
            System.out.println("Сервер запущен");

            // Бесконечный цикл для обработки входящих подключений
            while (true) {
                try (ClientServer clientServer = new ClientServer(serverSocket)) {
                    // Чтение запроса от клиента
                    clientRequest = clientServer.readLine();

                    // Синхронизация для обеспечения потокобезопасности
                    synchronized (this) {
                        // Если запрос равен "1", отправляем комбинированный ответ
                        if (clientRequest.equals("1")) {
                            clientServer.writeLine(combinedResponse);
                        } else {
                            // Если запрос уже есть в карте, отправляем сохраненный ответ
                            if (requestDataMap.containsKey(clientRequest)) {
                                serverResponse = requestDataMap.get(clientRequest).toString();
                                clientServer.writeLine(clientRequest);
                                clientServer.writeLine(serverResponse);
                                System.out.println("Запрос: " + clientRequest);
                                System.out.println("Ответ: " + serverResponse);
                                combinedResponse = clientRequest + serverResponse;
                                System.out.println("Комбинированный ответ: " + combinedResponse);
                            } else {
                                // Если запрос новый, читаем новый ответ, сохраняем его в карте и отправляем клиенту
                                serverResponse = clientServer.readLine();
                                requestDataMap.put(clientRequest, Integer.valueOf(serverResponse));
                                clientServer.writeLine(clientRequest);
                                clientServer.writeLine(serverResponse);
                                System.out.println("Карта данных: " + requestDataMap);
                                combinedResponse = clientRequest + serverResponse;
                                System.out.println("Комбинированный ответ: " + combinedResponse);
                            }
                        }
                    }
                } catch (IOException e) {
                    // Обработка ошибок при подключении клиента
                    throw new RuntimeException("Обработка ошибок при подключении клиента", e);
                }
            }
        } catch (IOException e) {
            // Обработка ошибок при запуске сервера
            throw new RuntimeException("Ошибка при запуске сервера", e);
        }
    }
}
