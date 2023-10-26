package io.jacobking.simpleticket.gui.controller;

import io.jacobking.simpleticket.gui.navigation.Navigation;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {

    private final Navigation navigation;

    public Controller(Navigation navigation) {
        this.navigation = navigation;
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
