package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the Cinema App.
 */
public class MainApp extends Application {

    private Stage stage;

    /**
     * Starts the application.
     * 
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during loading
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout.fxml"));
        Parent root = loader.load();
        
        Controller controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root, 1440, 810);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setTitle("Cinema App");
        stage.show();
    }

    /**
     * Main method to launch the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
