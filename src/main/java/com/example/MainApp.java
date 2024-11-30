package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

// public class MainApp extends Application {
//     // private MainScene mainScene = new MainScene();

//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layout.fxml"));
//         Parent root = loader.load();

//         // Pobierz kontroler i ustaw Stage
//         Controller controller = loader.getController();
//         controller.setStage(primaryStage);

//         Scene scene = new Scene(root);
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }

    // @Override
    // public void start(Stage primaryStage) {
        // Button repertoireButton = new Button("Repertoire");
        // Button ticketsButton = new Button("Tickets");
        // Button snacksButton = new Button("Food");
        // Button accountsButton = new Button("Accounts");

        // VBox sideBar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        // sideBar.getStyleClass().add("sidebar");
        
        // VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        // container.getStyleClass().add("content");
        
        // Region separator = new Region();
        // separator.getStyleClass().add("separator");
        
        // HBox layout = new HBox(0, sideBar, separator, container);
        // layout.getStyleClass().add("vbox");
        
        // Scene mainScene = new Scene(layout, width, height);
        // mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        // repertoireButton.setOnAction(e -> loadPage(primaryStage, sideBar, new RepertoirePage()));
        // ticketsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellTicketsPage()));
        // snacksButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellFoodPage()));
        // accountsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new AccountsPage()));

    //     primaryStage.setTitle("Main Application");
    //     primaryStage.setScene(this.mainScene.getScene());
    //     primaryStage.show();
    // }
    // private void loadPage(Stage stage, VBox sideBar, Page page) {
    //     VBox container = page.getPage();
    //     container.getStyleClass().add("content");

    //     Region separator = new Region();
    //     separator.getStyleClass().add("separator");
        
    //     HBox layout = new HBox(0, sideBar, separator, container);
    //     layout.getStyleClass().add("vbox");

    //     Scene scene = new Scene(layout, width, height);
    //     scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    //     stage.setScene(scene);
    // }
//     public static void main(String[] args) {
//         launch(args);
//     }
// }