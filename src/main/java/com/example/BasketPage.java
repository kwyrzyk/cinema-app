package com.example;

import com.example.database.db_classes.Basket;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BasketPage implements Page {
    private Basket basket;
    private final VBox basketBox = new VBox();

    public BasketPage(Basket basket){
        this.basket = basket;
    }
    @Override
    public VBox getPage() {
        createContent();
        return basketBox;
    }

    private void createContent() {
        basketBox.getChildren().clear();
        
        Label pageTitle = new Label("Basket");
        pageTitle.getStyleClass().add("page-title");

        Label basketLabel = new Label(basket.toString());
        basketLabel.getStyleClass().add("item");
        basketBox.getChildren().addAll(pageTitle, basketLabel);
        basketBox.getStyleClass().addAll("page", "basket-page");
    }
}