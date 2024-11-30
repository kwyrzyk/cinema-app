package com.example;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class Layout {
    private VBox sideBar;
    private VBox optionsBar;
    private VBox container;

    public Layout(VBox sideBar, VBox optionsBar, VBox container) {
        this.sideBar = sideBar;
        this.optionsBar = optionsBar;
        this.container = container;
    }

    public void changeSideBar(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    public void changeOptionsBar(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    public void changeContainer(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    public HBox getLayout() {
        return new HBox(this.sideBar, this.optionsBar, this.container);
    }
}
