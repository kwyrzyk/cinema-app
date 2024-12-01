package com.example;

import com.example.database.db_classes.Basket;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BasketPage implements Page {
    private Basket basket;
    private final VBox content = new VBox();

    public BasketPage(Basket basket){
        this.basket = basket;
    }
    @Override
    public VBox getPage() {
        VBox contentContainer = createContent();

        HBox mainLayout = new HBox(0, contentContainer);
        mainLayout.getStyleClass().add("content");

        VBox finalLayout = new VBox(0, mainLayout);
        finalLayout.getStyleClass().add("newpage");

        return finalLayout;
    }

    private VBox createContent() {
        content.setSpacing(10);
        content.getStyleClass().add("content");
        content.getChildren().clear();
        Label itemLabel = new Label(basket.toString());
        content.getChildren().add(itemLabel);
        VBox contentContainer = new VBox(10, content);
        contentContainer.getStyleClass().add("content");

        return contentContainer;
    }
}