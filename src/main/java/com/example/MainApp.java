package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private final int height = 900;
    private final int width = 800;

    @Override
    public void start(Stage primaryStage) {
        Sidebar sidebar = new Sidebar(primaryStage, this);

        ContentContainer contentContainer = new ContentContainer();

        HBox layout = new HBox(0, sidebar.getSidebar(), contentContainer.getContainer());
        layout.getStyleClass().add("vbox");

        Scene mainScene = new Scene(layout, width, height);
        mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
 
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void loadPage(Stage primaryStage, VBox sideBar, Page page) {
        VBox content = page.getPage();
        content.getStyleClass().add("content");

        HBox layout = new HBox(0, sideBar, content);
        layout.getStyleClass().add("vbox");

        Scene scene = new Scene(layout, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
