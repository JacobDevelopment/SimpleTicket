package io.jacobking.simpleticket.gui.screen.impl.ticket;

import io.jacobking.simpleticket.gui.controller.impl.QuickUpdateController;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class QuickUpdateScreen extends Screen {
    public QuickUpdateScreen() {
        super(Route.QUICK_UPDATE);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        if (objects.length == 0)
            return;

        final TicketModel model = (TicketModel) objects[0];
        if (model == null)
            return;

        final QuickUpdateController controller = new QuickUpdateController(model);

        setController(controller);
        super.display(isModal, objects);
    }
}
