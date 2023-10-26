package io.jacobking.simpleticket.gui.controller.impl;


import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.DepartmentProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.CompanyComboBox;
import io.jacobking.simpleticket.gui.view.combobox.DepartmentComboBox;
import io.jacobking.simpleticket.tables.pojos.Employee;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewEmployeeController extends Controller {

    private final CompanyProctor companyProctor;
    private final DepartmentProctor departmentProctor;
    private final EmployeeProctor employeeProctor;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField titleField;
    @FXML private TextField emailField; // TODO: Implement e-mail regex pattern checking.

    @FXML private CompanyComboBox companyBox;
    @FXML private DepartmentComboBox departmentBox;

    @FXML private Button createButton;

    public NewEmployeeController() {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
        this.departmentProctor = Proctor.getInstance().department();
        this.employeeProctor = Proctor.getInstance().employee();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(Bindings.or(
                firstNameField.textProperty().isEmpty(),
                lastNameField.textProperty().isEmpty())
        );

        companyBox.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return companyProctor.getModelList().isEmpty();
        }));

        companyBox.setItems(companyProctor.getModelList());

        companyBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            final int companyId = newValue.getId();
            final BooleanBinding binding = getDepartmentListBinding(companyId);
            departmentBox.disableProperty().bind(binding);

            final var list = getModelList(companyId);
            if (list.isEmpty())
                return;

            departmentBox.setItems(list);
        }));
    }

    @FXML
    private void onCreate() {
        final Employee employee = new Employee()
                .setFirstName(firstNameField.getText())
                .setLastName(lastNameField.getText())
                .setTitle(titleField.getText())
                .setEmail(emailField.getText())
                .setDepartmentId(getDepartmentId())
                .setCompanyId(getCompanyId())
                .setCreatedOn(DateUtil.now());

        employeeProctor.create(employee);
        getNavigation().close(Route.NEW_EMPLOYEE);
    }

    @FXML
    private void onClearFields() {
        firstNameField.clear();
        lastNameField.clear();
        titleField.clear();
        emailField.clear();
        companyBox.getSelectionModel().clearSelection();
        departmentBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.NEW_EMPLOYEE);
    }

    private int getCompanyId() {
        final CompanyModel model = companyBox.getSelectionModel().getSelectedItem();
        return (model != null) ? model.getId() : 0;
    }

    private int getDepartmentId() {
        final DepartmentModel model = departmentBox.getSelectionModel().getSelectedItem();
        return (model != null) ? model.getId() : 0;
    }

    private BooleanBinding getDepartmentListBinding(final int companyId) {
        return Bindings.createBooleanBinding(() -> {
            return getModelList(companyId).isEmpty();
        });
    }

    private ObservableList<DepartmentModel> getModelList(final int companyId) {
        return departmentProctor.getModelList(departmentModel -> {
            return departmentModel.getCompanyId() == companyId;
        });
    }
}
