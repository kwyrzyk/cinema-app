package com.example;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SellFoodPage implements Page {
    @Override
    public VBox getPage() {
        
        Button snacks = new Button("Snacks");
        Button drinks = new Button("Drinks");

        VBox sideBar = new VBox(10, snacks, drinks);
        sideBar.getStyleClass().add("newsidebar");

        VBox container = new VBox(10, new Label("What you want"));
        container.getStyleClass().add("content");
        
        Region separator = new Region();
        separator.getStyleClass().add("separator");
        
        HBox main = new HBox(0, sideBar, separator, container);
        main.getStyleClass().add("content");
        
        VBox layout = new VBox(0, main);
        layout.getStyleClass().add("newpage");
        return layout;
    }
}
