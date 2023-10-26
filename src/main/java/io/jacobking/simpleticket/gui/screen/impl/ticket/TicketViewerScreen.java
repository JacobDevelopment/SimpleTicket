package io.jacobking.simpleticket.gui.screen.impl.ticket;

import io.jacobking.simpleticket.gui.controller.impl.TicketViewerController;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class TicketViewerScreen extends Screen {
    public TicketViewerScreen() {
        super(Route.TICKET_VIEWER);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        if (objects.length == 0)
            return;

        final TicketModel model = (TicketModel) objects[0];
        if (model == null)
            return;

        final TicketViewerController controller = new TicketViewerController(model);
        setController(controller);

        super.display(isModal, objects);
    }
}
