package com.example;

import javafx.scene.layout.VBox;

public class ContentContainer {
    private final VBox container;

    public ContentContainer() {
        container = new VBox();
        container.getStyleClass().add("content");
    }

    public VBox getContainer() {
        return container;
    }
}
