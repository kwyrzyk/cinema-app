package com.example;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SellTicketsPage implements Page{
    @Override
    public VBox getPage() {
        VBox container = new VBox(10, new Label("What you want"));
        container.getStyleClass().add("content");
        HBox main = new HBox(0,container);
        main.getStyleClass().add("content");
        
        VBox layout = new VBox(0, main);
        layout.getStyleClass().add("newpage");
        return layout;
    }
}

