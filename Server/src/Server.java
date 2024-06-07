import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Гидрометцентр");
        primaryStage.setScene(new Scene(root, 1000, 2000));
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}