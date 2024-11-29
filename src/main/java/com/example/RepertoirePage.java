package com.example;


import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class RepertoirePage implements Page {
    @Override
    public VBox getPage() {
        VBox layout = new VBox(new Label("Repertoire"));
        return layout;
    }
}
