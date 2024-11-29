package com.example;


import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SellFoodPage {

    public VBox getPage() {
        Label label = new Label("Welcome to the Sell Food Page");
        // Add other functionality for selling food here

        VBox layout = new VBox(10, label);
        return layout;
    }
}
