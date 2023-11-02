package io.jacobking.simpleticket.gui.screen.impl.employee;

import io.jacobking.simpleticket.gui.controller.impl.portal.EmployeePortalController;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;
import javafx.scene.control.TableView;


public class EmployeePortalScreen extends Screen {
    public EmployeePortalScreen() {
        super(Route.EMPLOYEE_PORTAL);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void display(boolean isModal, Object... objects) {
        if (objects.length == 0) {
            setController(new EmployeePortalController(null, null));
            super.display(isModal, objects);
            return;
        }

        final EmployeeModel model = (EmployeeModel) objects[0];
        final TableView<EmployeeModel> employeeTable = (TableView<EmployeeModel>) objects[1]; // seems hacky?
        if (model == null || employeeTable == null) {
            setController(new EmployeePortalController(null, null));
            super.display(isModal, objects);
            return;
        }

        setController(new EmployeePortalController(model, employeeTable));
        super.display(isModal, objects);
    }
}
