package io.jacobking.simpleticket.gui.controller.impl.portal;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
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
import io.jacobking.simpleticket.tables.pojos.Employee;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeePortalController extends Controller {

    private final TableView<EmployeeModel> employeeTable;
    private final EmployeeProctor employeeProctor;
    private final CompanyProctor companyProctor;
    private final DepartmentProctor departmentProctor;
    private final EmployeeModel model;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField titleField;

    @FXML private SearchableComboBox<CompanyModel> companyBox;
    @FXML private SearchableComboBox<DepartmentModel> departmentBox;

    @FXML private Button saveButton;

    public EmployeePortalController(EmployeeModel model, TableView<EmployeeModel> employeeTable) {
        super(Navigation.getInstance());
        this.employeeProctor = Proctor.getInstance().employee();
        this.companyProctor = Proctor.getInstance().company();
        this.departmentProctor = Proctor.getInstance().department();
        this.model = model;
        this.employeeTable = employeeTable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureCompanyBox();
        configureDepartmentBox();
        bindButton();

        if (model != null) {
            populateFields();
        }
    }

    @FXML
    private void onSaveEmployee() {
        if (model != null) {
            updateEmployee();
            return;
        }

        final Employee employee = new Employee()
                .setTitle(titleField.getText())
                .setCompanyId(getCompanyId())
                .setEmail(emailField.getText())
                .setDepartmentId(getDepartmentId())
                .setFirstName(firstNameField.getText())
                .setLastName(lastNameField.getText())
                .setCreatedOn(DateUtil.now());

        employeeProctor.create(employee);
        getNavigation().close(Route.EMPLOYEE_PORTAL);
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.EMPLOYEE_PORTAL);
    }

    private void configureCompanyBox() {
        companyBox.setItems(companyProctor.getModelList());
        companyBox.setConverter(new StringConverter<CompanyModel>() {
            @Override
            public String toString(CompanyModel object) {
                return object.getTitle();
            }

            @Override
            public CompanyModel fromString(String string) {
                return null;
            }
        });

        companyBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CompanyModel> call(ListView<CompanyModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CompanyModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                };
            }
        });

        companyBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                departmentBox.setItems(departmentProctor.getModelListFiltered(departmentModel
                        -> departmentModel.getCompanyId() == newValue.getId()));
            }
        }));
    }

    private void configureDepartmentBox() {
        departmentBox.setItems(departmentProctor.getModelList());
        departmentBox.setConverter(new StringConverter<DepartmentModel>() {
            @Override
            public String toString(DepartmentModel object) {
                return object.getTitle();
            }

            @Override
            public DepartmentModel fromString(String string) {
                return null;
            }
        });

        departmentBox.setCellFactory(new Callback<ListView<DepartmentModel>, ListCell<DepartmentModel>>() {
            @Override
            public ListCell<DepartmentModel> call(ListView<DepartmentModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(DepartmentModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                };
            }
        });
    }

    private void bindButton() {
        saveButton.disableProperty().bind(
                Bindings.or(
                        firstNameField.textProperty().isEmpty(),
                        lastNameField.textProperty().isEmpty()
                ).or(companyBox.getSelectionModel().selectedItemProperty().isNull())
        );
    }

    private void updateEmployee() {
        model.setEmail(emailField.getText());
        model.setFirstName(firstNameField.getText());
        model.setLastName(lastNameField.getText());
        model.setCompany(getCompanyId());
        model.setDepartment(getDepartmentId());
        model.setTitle(titleField.getText());
        employeeTable.refresh();

        final Employee employee = model.getAsPojo();
        Database.update(ServiceType.EMPLOYEE, employee);
        getNavigation().close(Route.EMPLOYEE_PORTAL);
    }

    private void populateFields() {
        firstNameField.setText(model.getFirstName());
        lastNameField.setText(model.getLastName());
        titleField.setText(model.getTitle());
        emailField.setText(model.getEmail());
        companyBox.getItems().forEach(cm -> {
            if (cm.getId() == model.getCompanyId()) {
                companyBox.getSelectionModel().select(cm);
            }
        });

        departmentBox.getItems().forEach(dm -> {
            if (dm.getId() == model.getDepartmentId()) {
                departmentBox.getSelectionModel().select(dm);
            }
        });
    }

    private int getCompanyId() {
        final CompanyModel model = companyBox.getSelectionModel().getSelectedItem();
        return model != null ? model.getId() : 0;
    }

    private int getDepartmentId() {
        final DepartmentModel model = departmentBox.getSelectionModel().getSelectedItem();
        return model != null ? model.getId() : 0;
    }


}
