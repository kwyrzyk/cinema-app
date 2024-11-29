package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private final int height = 900;
    private final int width = 800;

    @Override
    public void start(Stage primaryStage) {
        Button repertoireButton = new Button("Repertoire");
        Button ticketsButton = new Button("Tickets");
        Button snacksButton = new Button("Food");
        Button accountsButton = new Button("Accounts");

        VBox sideBar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        sideBar.getStyleClass().add("sidebar");
        
        VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        container.getStyleClass().add("content");
        
        Region separator = new Region();
        separator.getStyleClass().add("separator");
        
        HBox layout = new HBox(0, sideBar, separator, container);
        layout.getStyleClass().add("vbox");
        
        Scene mainScene = new Scene(layout, width, height);
        mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        repertoireButton.setOnAction(e -> loadPage(primaryStage, sideBar, new RepertoirePage()));
        ticketsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellTicketsPage()));
        snacksButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellFoodPage()));
        accountsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new AccountsPage()));

        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    private void loadPage(Stage stage, VBox sideBar, Page page) {
        VBox container = page.getPage();
        container.getStyleClass().add("content");

        Region separator = new Region();
        separator.getStyleClass().add("separator");
        
        HBox layout = new HBox(0, sideBar, separator, container);
        layout.getStyleClass().add("vbox");

        Scene scene = new Scene(layout, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setScene(scene);
    }
    public static void main(String[] args) {
        launch(args);
    }
}