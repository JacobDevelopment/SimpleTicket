package io.jacobking.simpleticket.gui.view.table;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.function.BiConsumer;


public class EmployeeTable extends TableView<EmployeeModel> {

    private final TableColumn<EmployeeModel, String> idColumn = new TableColumn<>("ID");
    private final TableColumn<EmployeeModel, String> companyIdColumn = new TableColumn<>("Company");
    private final TableColumn<EmployeeModel, String> firstNameColumn = new TableColumn<>("First Name");
    private final TableColumn<EmployeeModel, String> lastNameColumn = new TableColumn<>("Last Name");
    private final TableColumn<EmployeeModel, String> departmentIdColumn = new TableColumn<>("Department");
    private final TableColumn<EmployeeModel, String> titleColumn = new TableColumn<>("Title");
    private final TableColumn<EmployeeModel, String> emailColumn = new TableColumn<>("Email");
    private final TableColumn<EmployeeModel, String> createdOnColumn = new TableColumn<>("Created On");

    public EmployeeTable() {
        setPlaceholder(getLabel());
        addColumns();
        setCellValueFactory();
    }

    @SuppressWarnings("unchecked")
    private void addColumns() {
        getColumns().addAll(idColumn, companyIdColumn, firstNameColumn, lastNameColumn, departmentIdColumn, titleColumn, emailColumn, createdOnColumn);
    }

    private void setCellValueFactory() {
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asString());
        companyIdColumn.setCellValueFactory(data -> data.getValue().companyIdProperty().asString());
        firstNameColumn.setCellValueFactory(data -> data.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(data -> data.getValue().lastNameProperty());
        departmentIdColumn.setCellValueFactory(data -> data.getValue().departmentIdProperty().asString());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        createdOnColumn.setCellValueFactory(data -> data.getValue().createdProperty());
    }

    public void convertCompanyColumn() {
        companyIdColumn.setCellValueFactory(value -> {
            final Company company = Database.fetch(ServiceType.COMPANY, value.getValue().getCompanyId());
            if (company == null)
                return value.getValue().idProperty().asString();
            return new SimpleStringProperty(company.getTitle());
        });
    }

    public void convertDepartmentColumn() {
        departmentIdColumn.setCellValueFactory(value -> {
            final Department department = Database.fetch(ServiceType.DEPARTMENT, value.getValue().getDepartmentId());
            if (department == null)
                return value.getValue().departmentIdProperty().asString();
            return new SimpleStringProperty(department.getTitle());
        });
    }

    public void setEditCommits(final boolean state) {
        setEditHandler(titleColumn, (newValue, model) -> {
            model.setTitle(newValue);
        });

        setEditHandler(firstNameColumn, (newValue, model) -> {
            model.setFirstName(newValue);
        });

        setEditHandler(lastNameColumn, (newValue, model) -> {
            model.setLastName(newValue);
        });

        setEditHandler(emailColumn, (newValue, model) -> {
            model.setEmail(newValue);
        });

        setEditable(state);
    }

    private void setEditHandler(final TableColumn<EmployeeModel, String> column, BiConsumer<String, EmployeeModel> modelConsumer) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            final var departmentItems = event.getTableView().getItems();
            final int rowPosition = event.getTablePosition().getRow();

            final EmployeeModel model = departmentItems.get(rowPosition);
            if (model == null)
                return;

            modelConsumer.accept(event.getNewValue(), model);
            Database.update(ServiceType.DEPARTMENT, model.getAsPojo());
        });
    }

    private Label getLabel() {
        final Label label = new Label("There are no employees.");
        label.getStyleClass().add("empty-label");
        return label;
    }
}
