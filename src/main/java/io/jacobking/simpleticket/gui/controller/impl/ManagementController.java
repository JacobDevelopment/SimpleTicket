package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.DepartmentProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.table.CompanyTable;
import io.jacobking.simpleticket.gui.view.table.DepartmentTable;
import io.jacobking.simpleticket.gui.view.table.EmployeeTable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController extends Controller {


    public ManagementController() {
        super(Navigation.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
