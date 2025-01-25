package com.example;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * Represents the layout of the application.
 */
public class Layout {
    private VBox sideBar;
    private VBox optionsBar;
    private VBox container;

    /**
     * Constructs a Layout with the specified side bar, options bar, and container.
     * @param sideBar the side bar of the layout
     * @param optionsBar the options bar of the layout
     * @param container the container of the layout
     */
    public Layout(VBox sideBar, VBox optionsBar, VBox container) {
        this.sideBar = sideBar;
        this.optionsBar = optionsBar;
        this.container = container;
    }

    /**
     * Changes the side bar of the layout.
     * @param newSideBar the new side bar to be set
     */
    public void changeSideBar(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    /**
     * Changes the options bar of the layout.
     * @param newSideBar the new options bar to be set
     */
    public void changeOptionsBar(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    /**
     * Changes the container of the layout.
     * @param newSideBar the new container to be set
     */
    public void changeContainer(VBox newSideBar) {
        this.sideBar = newSideBar;
    }

    /**
     * Returns the HBox containing the layout.
     * @return the HBox containing the layout
     */
    public HBox getLayout() {
        return new HBox(this.sideBar, this.optionsBar, this.container);
    }
}
