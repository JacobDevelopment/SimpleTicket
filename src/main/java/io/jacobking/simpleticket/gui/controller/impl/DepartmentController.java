package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.DepartmentProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DepartmentController extends Controller {

    private final CompanyProctor companyProctor;
    private final DepartmentProctor departmentProctor;
    private final EmployeeProctor employeeProctor;

    @FXML private Label companyLabel;
    @FXML private Label departmentLabel;

    @FXML private TableView<DepartmentModel> departmentTable;
    @FXML private TableColumn<DepartmentModel, String> companyColumn;
    @FXML private TableColumn<DepartmentModel, String> nameColumn;
    @FXML private TableColumn<DepartmentModel, String> abbreviationColumn;
    @FXML private TableColumn<DepartmentModel, String> supervisorColumn;

    @FXML private CheckComboBox<CompanyModel> hideBox;

    private final FilteredList<DepartmentModel> filteredData;

    public DepartmentController() {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
        this.departmentProctor = Proctor.getInstance().department();
        this.employeeProctor = Proctor.getInstance().employee();
        this.filteredData = new FilteredList<>(departmentProctor.getModelList(), dm -> true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        configureHideBox();
        configureLabels();
    }

    @FXML
    private void onCreateDepartment() {
        getNavigation().display(Route.DEPARTMENT_PORTAL, true);
    }

    @FXML
    private void onDeleteDepartment() {
        final DepartmentModel model = departmentTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            departmentProctor.delete(model.getId());
        }
    }

    @FXML
    private void onEditDepartment() {
        final DepartmentModel model = departmentTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            getNavigation().display(Route.DEPARTMENT_PORTAL, true, model);
        }
    }

    @FXML
    private void onSearch() {

    }

    private void configureLabels() {
        departmentLabel.setText(String.valueOf(departmentProctor.getModelList().size()));
        departmentProctor.getModelList().addListener((ListChangeListener<? super DepartmentModel>) c -> {
            while (c.next()) {
                final int size = departmentProctor.getModelList().size();
                departmentLabel.setText(String.valueOf(size));
            }
        });

        companyLabel.setText(String.valueOf(companyProctor.getModelList().size()));
        companyProctor.getModelList().addListener((ListChangeListener<? super CompanyModel>) c -> {
            while (c.next()) {
                final int size = companyProctor.getModelList().size();
                companyLabel.setText(String.valueOf(size));
            }
        });
    }

    private void configureTable() {
        departmentTable.setItems(departmentProctor.getModelList());
        companyColumn.setCellValueFactory(data -> {
            final int companyId = data.getValue().getCompanyId();
            final Company company = Database.fetch(ServiceType.COMPANY, companyId);
            if (company != null) {
                return new SimpleStringProperty(company.getTitle());
            }
            return new SimpleStringProperty(String.valueOf(data.getValue().getCompanyId()));
        });

        nameColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        abbreviationColumn.setCellValueFactory(data -> data.getValue().abbreviationProperty());

        supervisorColumn.setCellValueFactory(data -> {
            final int supervisorId = data.getValue().getSupervisorId();
            final Employee employee = Database.fetch(ServiceType.EMPLOYEE, supervisorId);
            if (employee != null && employee.getId() == 0) {
                return new SimpleStringProperty("Not Available");
            }
            if (employee != null) {
                return new SimpleStringProperty(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
            }

            return new SimpleStringProperty(String.valueOf(data.getValue().getSupervisorId()));
        });
    }

    private void configureHideBox() {
        hideBox.getItems().addAll(companyProctor.getModelList());
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
        final FilteredList<DepartmentModel> filteredList = new FilteredList<>(departmentProctor.getModelList());
        hideBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super CompanyModel>) change -> {
            Predicate<DepartmentModel> combinedPredicate = dm -> false;

            for (CompanyModel cm : hideBox.getCheckModel().getCheckedItems()) {
                final int companyId = cm.getId();
                combinedPredicate = combinedPredicate.or(dm -> dm.getCompanyId() == companyId);
            }

            filteredList.setPredicate(combinedPredicate);
            departmentTable.setItems(filteredList);
        });
    }

}
