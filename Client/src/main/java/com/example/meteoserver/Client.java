package com.example.meteoserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("src/main/resources/com.example.meteoserver/fxml/sample.fxml"));
        primaryStage.setTitle("Гидрометцентр");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}

