package ClientServers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClServ implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ClServ(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.reader = createReader();
        this.writer = createWriter();
    }

    public ClServ(ServerSocket server) throws IOException {
        this.socket = server.accept();
        this.reader = createReader();
        this.writer = createWriter();
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void writeLine(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}