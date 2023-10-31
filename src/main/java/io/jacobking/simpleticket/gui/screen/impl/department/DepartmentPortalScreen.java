package io.jacobking.simpleticket.gui.screen.impl.department;

import io.jacobking.simpleticket.gui.controller.impl.DepartmentController;
import io.jacobking.simpleticket.gui.controller.impl.DepartmentPortalController;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class DepartmentPortalScreen extends Screen {
    public DepartmentPortalScreen() {
        super(Route.DEPARTMENT_PORTAL);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        if (objects.length == 0) {
            setController(new DepartmentPortalController(null));
            super.display(isModal, objects);
            return;
        }

        final DepartmentModel model = (DepartmentModel) objects[0];
        if (model == null) {
            return;
        }

        setController(new DepartmentPortalController(model));
        super.display(isModal, objects);
    }
}
