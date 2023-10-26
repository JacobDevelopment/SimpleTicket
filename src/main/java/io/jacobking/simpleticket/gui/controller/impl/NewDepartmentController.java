package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.DepartmentProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.CompanyComboBox;
import io.jacobking.simpleticket.gui.view.combobox.EmployeeComboBox;
import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class NewDepartmentController extends Controller {

    private final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        final String input = change.getText();
        if (input.matches("[0-9]*"))
            return change;
        return null;
    };

    private final DepartmentProctor proctor;
    private final EmployeeProctor employeeProctor;
    private final CompanyProctor companyProctor;

    @FXML private TextField nameField;
    @FXML private TextField abbreviationField;

    @FXML private CompanyComboBox companyBox;
    @FXML private TextField employeeIdField;
    @FXML private EmployeeComboBox supervisorBox;

    @FXML private Button createButton;

    public NewDepartmentController() {
        super(Navigation.getInstance());
        this.proctor = Proctor.getInstance().department();;
        this.employeeProctor = Proctor.getInstance().employee();
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(nameField.textProperty().isEmpty());
        configureCompanyBox();
        configureEmployeeBox();
        configureTextField();
    }

    @FXML
    private void onCreate() {
        final String title = nameField.getText();
        final String abbrev = getAbbreviation(abbreviationField.getText());

        final EmployeeModel employeeModel = supervisorBox.getSelectionModel().getSelectedItem();
        final int supervisorId = employeeModel != null ? employeeModel.getId() : 0;

        final CompanyModel model = companyBox.getSelectionModel().getSelectedItem();
        final int companyId = (model != null) ? model.getId() : 0;

        proctor.create(new Department()
                .setTitle(title)
                .setAbbreviation(abbrev)
                .setSupervisorId(supervisorId)
                .setCompanyId(companyId)
        );

        getNavigation().close(Route.NEW_DEPARTMENT);
    }

    @FXML
    private void onClearFields() {
        nameField.clear();
        abbreviationField.clear();
        employeeIdField.clear();
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.NEW_DEPARTMENT);
    }

    private void configureCompanyBox() {
        companyBox.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return companyProctor.getModelList().isEmpty();
        }));

        companyBox.setItems(companyProctor.getModelList());
    }

    private void configureEmployeeBox() {
        final BooleanBinding binding = companyBox.getSelectionModel().selectedItemProperty().isNull();
        supervisorBox.disableProperty().bind(binding);
        employeeIdField.disableProperty().bind(binding);

        companyBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            final int id = (newValue != null) ? newValue.getId() : 0;

            final ObservableList<EmployeeModel> employees = employeeProctor.getModelList(model -> model.getCompanyId() == id);
            if (employees.isEmpty())
                return;

            supervisorBox.setItems(employees);
        }));
    }

    private void configureTextField() {
        employeeIdField.setTextFormatter(new TextFormatter<String>(integerFilter));
        employeeIdField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                supervisorBox.getSelectionModel().clearSelection();
                return;
            }

            final int id = Integer.parseInt(newValue);
            supervisorBox.getItems().forEach(model -> {
                final int modelId = model.getId();
                if (modelId == id)
                    supervisorBox.getSelectionModel().select(model);
            });
        }));
    }

    private String getAbbreviation(final String abbreviation) {
        if (abbreviation.isEmpty()) {
            final String title = nameField.getText();
            final String[] split = title.split("\\s+");
            if (split.length <= 1) {
                return title.substring(0, 2);
            }

            return String.format("%s%s", split[0].charAt(0), split[1].charAt(0));
        }
        return abbreviation;
    }
}
