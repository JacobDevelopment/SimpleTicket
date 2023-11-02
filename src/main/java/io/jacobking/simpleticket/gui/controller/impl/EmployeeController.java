package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.alert.Alerts;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class EmployeeController extends Controller {

    private final EmployeeProctor employeeProctor;
    private final CompanyProctor companyProctor;

    @FXML private Label employeeLabel;

    @FXML private TableView<EmployeeModel> employeeTable;
    @FXML private TableColumn<EmployeeModel, String> companyColumn;
    @FXML private TableColumn<EmployeeModel, String> nameColumn;
    @FXML private TableColumn<EmployeeModel, String> departmentColumn;
    @FXML private TableColumn<EmployeeModel, String> titleColumn;
    @FXML private TableColumn<EmployeeModel, String> emailColumn;

    @FXML private TextField searchField;
    @FXML private CheckComboBox<CompanyModel> hideBox;

    public EmployeeController() {
        super(Navigation.getInstance());
        this.employeeProctor = Proctor.getInstance().employee();
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeLabel.setText(String.valueOf(employeeProctor.getModelList().size()));
        employeeProctor.getModelList().addListener((ListChangeListener<? super EmployeeModel>) c -> {
            while (c.next()) {
                final int size = employeeProctor.getModelList().size();
                employeeLabel.setText(String.valueOf(size));
            }
        });

        configureTable();
        configureHideBox();
        configureListeners();
    }

    @FXML
    private void onCreateEmployee() {
        getNavigation().display(Route.EMPLOYEE_PORTAL, true);
    }

    @FXML
    private void onDeleteEmployee() {
        final EmployeeModel model = employeeTable.getSelectionModel().getSelectedItem();
        if (model == null) {
            Alerts.notSelected();
            return;
        }

        Alerts.showDefaultConfirmation().ifPresent(type -> {
            if (type == ButtonType.YES) {
                employeeProctor.delete(model.getId());
            }
        });
    }

    @FXML
    private void onEditEmployee() {
        final EmployeeModel model = employeeTable.getSelectionModel().getSelectedItem();
        if (model == null) {
            Alerts.notSelected();
            return;
        }
        getNavigation().display(Route.EMPLOYEE_PORTAL, true, model, employeeTable);
    }

    @FXML
    private void onSearch() {
        final String text = searchField.getText();
        if (text.isEmpty())
            return;

        employeeTable.getItems().forEach(em -> {
            final String first = em.getFirstName();
            if (first.contains(text)) {
                selectModel(em);
                return;
            }

            final String last = em.getLastName();
            if (last.contains(text)) {
                selectModel(em);
                return;
            }

            final String title = em.getTitle();
            if (title.contains(text)) {
                selectModel(em);
                return;
            }

            final String email = em.getEmail();
            if (email.contains(text)) {
                selectModel(em);
            }
        });
    }

    private void selectModel(final EmployeeModel model) {
        employeeTable.getSelectionModel().select(model);
    }

    private void configureTable() {
        employeeTable.setItems(employeeProctor.getModelList());

        companyColumn.setCellValueFactory(data -> {
            final int companyId = data.getValue().getCompanyId();
            final Company company = Database.fetch(ServiceType.COMPANY, companyId);
            if (company != null && company.getId() == 0) {
                return new SimpleStringProperty("None.");
            }
            if (company != null) {
                return new SimpleStringProperty(company.getTitle());
            }
            return new SimpleStringProperty(String.valueOf(companyId));
        });

        nameColumn.setCellValueFactory(data -> {
            return new SimpleStringProperty(String.format("%s %s",
                    data.getValue().getFirstName(),
                    data.getValue().getLastName()
            ));
        });

        departmentColumn.setCellValueFactory(data -> {
            final int departmentId = data.getValue().getDepartmentId();
            final Department department = Database.fetch(ServiceType.DEPARTMENT, departmentId);
            if (department != null && department.getId() == 0) {
                return new SimpleStringProperty("None.");
            }

            if (department != null) {
                return new SimpleStringProperty(department.getTitle());
            }
            return new SimpleStringProperty(String.valueOf(departmentId));
        });

        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

    }

    private void configureHideBox() {
        hideBox.getItems().addAll(companyProctor.getModelList());

        companyProctor.getModelList().addListener((ListChangeListener<? super CompanyModel>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    final CompanyModel model = c.getAddedSubList().get(0);
                    hideBox.getItems().add(model);
                } else if (c.wasRemoved()) {
                    final CompanyModel model = c.getRemoved().get(0);
                    hideBox.getItems().remove(model);
                }
            }
        });

        hideBox.getItems().forEach(cm -> {
            hideBox.getCheckModel().check(cm);
        });

        setConverter();
        setHidingConfiguration();
    }

    private void setConverter() {
        hideBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(CompanyModel object) {
                return object.getTitle();
            }

            @Override
            public CompanyModel fromString(String string) {
                return null;
            }
        });
    }

    private void setHidingConfiguration() {
        final FilteredList<EmployeeModel> filteredList = new FilteredList<>(employeeProctor.getModelList());
        hideBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super CompanyModel>) change -> {
            Predicate<EmployeeModel> combinedPredicate = dm -> false;

            for (CompanyModel cm : hideBox.getCheckModel().getCheckedItems()) {
                if (cm == null)
                    continue;
                final int companyId = cm.getId();
                combinedPredicate = combinedPredicate.or(dm -> dm.getCompanyId() == companyId);
            }

            filteredList.setPredicate(combinedPredicate);
            employeeTable.setItems(filteredList);
        });
    }

    private void configureListeners() {

    }
}
