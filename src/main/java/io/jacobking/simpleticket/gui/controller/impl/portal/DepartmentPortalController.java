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
import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentPortalController extends Controller {

    private final CompanyProctor companyProctor;
    private final DepartmentProctor departmentProctor;
    private final EmployeeProctor employeeProctor;
    private final DepartmentModel model;

    @FXML private TextField departmentNameField;
    @FXML private TextField departmentAbbreviationField;
    @FXML private TextField departmentDescriptionField;

    @FXML private SearchableComboBox<CompanyModel> companyBox;
    @FXML private SearchableComboBox<EmployeeModel> employeeBox;

    @FXML private Button saveButton;

    public DepartmentPortalController(DepartmentModel model) {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
        this.departmentProctor = Proctor.getInstance().department();
        this.employeeProctor = Proctor.getInstance().employee();
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureCompanyBox();
        configureEmployeeBox();
        setBindings();

        if (model != null) {
            populateFields();
        }
    }

    @FXML
    private void onSaveDepartment() {
        if (model != null) {
            updateDepartment();
            return;
        }

        final Department department = new Department()
                .setTitle(departmentNameField.getText())
                .setAbbreviation(departmentAbbreviationField.getText())
                .setCompanyId(getCompanyId())
                .setDescription(departmentDescriptionField.getText())
                .setSupervisorId(getSupervisorId());

        departmentProctor.create(department);
        getNavigation().close(Route.DEPARTMENT_PORTAL);
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.DEPARTMENT_PORTAL);
    }

    private int getCompanyId() {
        return companyBox.getSelectionModel()
                .getSelectedItem()
                .getId();
    }

    private int getSupervisorId() {
        final var item = employeeBox.getSelectionModel().getSelectedItem();
        return item == null ? 0 : item.getId();
    }

    private void updateDepartment() {
        model.setTitle(departmentNameField.getText());
        model.setAbbreviation(departmentAbbreviationField.getText());
        model.setDescription(departmentDescriptionField.getText());
        model.setCompanyId(getCompanyId());
        model.setSupervisorId(getSupervisorId());

        Database.update(ServiceType.DEPARTMENT, model.getAsPojo());
        getNavigation().close(Route.DEPARTMENT_PORTAL);
    }

    private void populateFields() {
        departmentNameField.setText(model.getTitle());
        departmentAbbreviationField.setText(model.getAbbreviation());

        for (final CompanyModel cm : companyBox.getItems()) {
            if (cm.getId() == model.getCompanyId()) {
                companyBox.getSelectionModel().select(cm);
                break;
            }
        }

        for (final EmployeeModel em : employeeBox.getItems()) {
            if (em.getId() == model.getSupervisorId()) {
                employeeBox.getSelectionModel().select(em);
                break;
            }
        }
    }

    private void setBindings() {
        saveButton.disableProperty().bind(
                companyBox.getSelectionModel().selectedItemProperty().isNull()
                        .or(departmentNameField.textProperty().isEmpty())
        );
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

        companyBox.setCellFactory(new Callback<ListView<CompanyModel>, ListCell<CompanyModel>>() {
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
    }

    private void configureEmployeeBox() {
        employeeBox.setItems(employeeProctor.getModelList());

        employeeBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeModel object) {
                return String.format("%s %s", object.getFirstName(), object.getLastName());
            }

            @Override
            public EmployeeModel fromString(String string) {
                return null;
            }
        });

        employeeBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<EmployeeModel> call(ListView<EmployeeModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(EmployeeModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
                        }
                    }
                };
            }
        });
    }
}
