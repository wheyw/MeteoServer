package ClientServers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer implements Closeable {
    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    // Конструктор для клиента, инициализирует соединение с сервером по IP и порту
    public ClientServer(String ipAddress, int port) {
        try {
            this.socket = new Socket(ipAddress, port);
            this.bufferedReader = createBufferedReader();
            this.bufferedWriter = createBufferedWriter();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка подключения к серверу", e);
        }
    }

    // Конструктор для сервера, принимает входящее соединение
    public ClientServer(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.bufferedReader = createBufferedReader();
            this.bufferedWriter = createBufferedWriter();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при приеме клиентского соединения", e);
        }
    }

    // Создание BufferedReader для чтения данных из сокета
    private BufferedReader createBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Создание BufferedWriter для записи данных в сокет
    private BufferedWriter createBufferedWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // Метод для отправки строки сообщения на сервер/клиент
    public void writeLine(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения из сокета", e);
        }
    }

    // Метод для чтения строки сообщения с сервера/клиента
    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения из сокета", e);
        }
    }

    // Метод закрытия ресурсов (сокета, BufferedReader и BufferedWriter)
    @Override
    public void close() throws IOException {
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
    }
}
