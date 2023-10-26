package io.jacobking.simpleticket.gui.screen.impl.employee;

import io.jacobking.simpleticket.gui.controller.impl.NewEmployeeController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class NewEmployeeScreen extends Screen {
    public NewEmployeeScreen() {
        super(Route.NEW_EMPLOYEE);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        final NewEmployeeController controller = new NewEmployeeController();

        setController(controller);
        super.display(isModal, objects);
    }
}
