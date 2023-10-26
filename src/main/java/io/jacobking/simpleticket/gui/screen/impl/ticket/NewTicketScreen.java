package io.jacobking.simpleticket.gui.screen.impl.ticket;

import io.jacobking.simpleticket.gui.controller.impl.NewTicketController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class NewTicketScreen extends Screen {
    public NewTicketScreen() {
        super(Route.NEW_TICKET);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        final NewTicketController controller = new NewTicketController();
        setController(controller);
        super.display(isModal, objects);
    }
}
