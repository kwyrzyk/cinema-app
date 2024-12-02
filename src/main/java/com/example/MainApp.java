package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout.fxml"));
        Parent root = loader.load();
        
        // Przekazanie Stage do kontrolera
        Controller controller = loader.getController();
        controller.setStage(stage);

        // Utworzenie sceny i ustawienie na Stage
        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setTitle("Cinema App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
