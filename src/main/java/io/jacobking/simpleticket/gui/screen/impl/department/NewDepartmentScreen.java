package io.jacobking.simpleticket.gui.screen.impl.department;

import io.jacobking.simpleticket.gui.controller.impl.NewDepartmentController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class NewDepartmentScreen extends Screen {
    public NewDepartmentScreen() {
        super(Route.NEW_DEPARTMENT);
    }


    @Override
    public void display(boolean isModal, Object... objects) {
        final NewDepartmentController controller = new NewDepartmentController();
        setController(controller);
        super.display(isModal, objects);
    }
}
