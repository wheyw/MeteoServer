package ClientServers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClServ implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ClServ(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader(socket.getInputStream());
            this.writer = createWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error creating client socket", e);
        }
    }

    public ClServ(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.reader = createReader(socket.getInputStream());
            this.writer = createWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error accepting client connection", e);
        }
    }

    private BufferedReader createReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private BufferedWriter createWriter(OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing message", e);
        }
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading message", e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing resources", e);
        }
    }
}