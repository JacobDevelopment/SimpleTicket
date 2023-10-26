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

    private final DepartmentProctor departmentProctor;
    private final EmployeeProctor employeeProctor;
    private final CompanyProctor companyProctor;

    @FXML private DepartmentTable departmentTable;
    @FXML private EmployeeTable employeeTable;
    @FXML private CompanyTable companyTable;

    private final BooleanProperty companyEditState = new SimpleBooleanProperty(false);
    @FXML private Button editCompanyButton;

    private final BooleanProperty departmentEditState = new SimpleBooleanProperty(false);
    @FXML private Button editDepartmentButton;

    private final BooleanProperty employeeEditState = new SimpleBooleanProperty(false);
    @FXML private Button editEmployeeButton;

    public ManagementController() {
        super(Navigation.getInstance());
        this.departmentProctor = Proctor.getInstance().department();
        this.employeeProctor = Proctor.getInstance().employee();
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmentTable.setItems(departmentProctor.getModelList());
        departmentTable.convertCompanyColumn();
        departmentTable.convertSupervisorColumn();

        employeeTable.setItems(employeeProctor.getModelList());
        employeeTable.convertCompanyColumn();
        employeeTable.convertDepartmentColumn();

        companyTable.setItems(companyProctor.getModelList());

        editCompanyButton.disableProperty().bind(companyTable.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void onNewDepartment() {
        getNavigation().display(Route.NEW_DEPARTMENT, true,
                departmentProctor, employeeProctor, companyProctor);
    }

    @FXML
    private void onDeleteDepartment() {
        final DepartmentModel model = departmentTable.getSelectionModel().getSelectedItem();
        if (model == null)
            return;
        departmentProctor.delete(model.getId());
    }

    @FXML
    private void onEditDepartment() {
        final boolean state = departmentEditState.getValue();
        departmentEditState.setValue(!state);
        toggleButtonStyle(editDepartmentButton, state);
        departmentTable.setEditCommits(departmentEditState.getValue());
    }

    @FXML
    private void onNewEmployee() {
        getNavigation().display(Route.NEW_EMPLOYEE, true,
                departmentProctor, employeeProctor, companyProctor);
    }

    @FXML
    private void onDeleteEmployee() {
        final EmployeeModel model = employeeTable.getSelectionModel().getSelectedItem();
        if (model == null)
            return;
        employeeProctor.delete(model.getId());
    }

    @FXML
    private void onEditEmployee() {
        final boolean state = employeeEditState.getValue();
        employeeEditState.setValue(!state);
        toggleButtonStyle(editEmployeeButton, state);
        employeeTable.setEditCommits(employeeEditState.getValue());
    }

    @FXML
    private void onNewCompany() {
        getNavigation().display(Route.NEW_COMPANY, true, companyProctor);
    }

    @FXML
    private void onDeleteCompany() {
        final CompanyModel model = companyTable.getSelectionModel().getSelectedItem();
        if (model == null)
            return;
        companyProctor.delete(model.getId());
    }

    @FXML
    private void onEditCompany() {
        final boolean state = companyEditState.getValue();
        companyEditState.setValue(!state);
        toggleButtonStyle(editCompanyButton, state);
        companyTable.setEditCommits(companyEditState.getValue());
    }

    private void toggleButtonStyle(final Button button, final boolean state) {
        if (!state) {
            button.getStyleClass().add("button-on");
            button.getStyleClass().remove("button-off");
        } else {
            button.getStyleClass().remove("button-on");
            button.getStyleClass().add("button-off");
        }
    }
}
