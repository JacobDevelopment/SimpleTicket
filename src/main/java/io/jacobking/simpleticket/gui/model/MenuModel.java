package io.jacobking.simpleticket.gui.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MenuModel {
    private static final String STYLE_CLASS = "active-pane";

    private final Pane sidePane;
    private final AnchorPane contentPane;

    public MenuModel(final Pane sidePane, final AnchorPane contentPane) {
        this.sidePane = sidePane;
        this.contentPane = contentPane;
    }

    public void enable() {
        addStyle();
        contentPane.setDisable(false);
        contentPane.setVisible(true);
    }

    public void disable() {
        removeStyle();
        contentPane.setDisable(true);
        contentPane.setVisible(false);
    }

    private void addStyle() {
        sidePane.getStyleClass().add(STYLE_CLASS);
    }

    private void removeStyle() {
        sidePane.getStyleClass().remove(STYLE_CLASS);
    }

}
