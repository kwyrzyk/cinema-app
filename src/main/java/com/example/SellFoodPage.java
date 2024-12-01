package com.example;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SellFoodPage implements Page {

    private final VBox content = new VBox();

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
        Label label = new Label("What you want");
        content.getChildren().add(label);
        content.setSpacing(10);
        content.getStyleClass().add("content");

        VBox contentContainer = new VBox(10, content);
        contentContainer.getStyleClass().add("content");

        return contentContainer;
    }
}
