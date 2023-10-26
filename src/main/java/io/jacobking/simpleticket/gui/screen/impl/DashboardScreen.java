package io.jacobking.simpleticket.gui.screen.impl;

import io.jacobking.simpleticket.core.SimpleTicket;
import io.jacobking.simpleticket.gui.controller.impl.DashboardController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;
import javafx.stage.Stage;

public class DashboardScreen extends Screen {
    public DashboardScreen() {
        super(Route.DASHBOARD, new DashboardController());
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        super.display(isModal, objects);
        final Stage stage = getStage();
        if (stage != null) {
            stage.setOnCloseRequest(event -> SimpleTicket.getInstance().shutdown());
        }
    }
}
