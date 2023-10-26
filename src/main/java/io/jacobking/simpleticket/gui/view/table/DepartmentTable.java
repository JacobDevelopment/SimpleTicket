package io.jacobking.simpleticket.gui.view.table;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.function.BiConsumer;


public class DepartmentTable extends TableView<DepartmentModel> {

    private final TableColumn<DepartmentModel, String> idColumn = new TableColumn<>("ID");
    private final TableColumn<DepartmentModel, String> companyIdColumn = new TableColumn<>("Company");
    private final TableColumn<DepartmentModel, String> titleColumn = new TableColumn<>("Title");
    private final TableColumn<DepartmentModel, String> abbreviationColumn = new TableColumn<>("Abbreviation");
    private final TableColumn<DepartmentModel, String> supervisorIdColumn = new TableColumn<>("Supervisor");

    public DepartmentTable() {
        addColumns();
        setCellValueFactory();
        setPlaceholder(getLabel());
    }

    @SuppressWarnings("unchecked")
    private void addColumns() {
        getColumns().addAll(idColumn, companyIdColumn, titleColumn, abbreviationColumn, supervisorIdColumn);
    }

    private void setCellValueFactory() {
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asString());
        companyIdColumn.setCellValueFactory(data -> data.getValue().companyIdProperty().asString());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        abbreviationColumn.setCellValueFactory(data -> data.getValue().abbreviationProperty());
        supervisorIdColumn.setCellValueFactory(data -> data.getValue().supervisorIdProperty().asString());
    }

    public void convertCompanyColumn() {
        companyIdColumn.setCellValueFactory(value -> {
            final int id = value.getValue().getCompanyId();
            final Company company = Database.fetch(ServiceType.COMPANY, id);
            if (company == null) {
                return value.getValue().companyIdProperty().asString();
            }

            return new SimpleStringProperty(company.getTitle());
        });
    }

    public void convertSupervisorColumn() {
        supervisorIdColumn.setCellValueFactory(value -> {
            final Employee employee = Database.fetch(ServiceType.EMPLOYEE, value.getValue().getId());
            if (employee == null)
                return value.getValue().idProperty().asString();

            return new SimpleStringProperty(String.format("%s %s",
                    employee.getFirstName(), employee.getLastName())
            );
        });
    }

    public void setEditCommits(final boolean state) {
        setEditHandler(titleColumn, (newValue, model) -> {
            model.setTitle(newValue);
        });

        setEditHandler(abbreviationColumn, (newValue, model) -> {
            model.setAbbreviation(newValue);
        });

        setEditable(state);
    }

    private void setEditHandler(final TableColumn<DepartmentModel, String> column, BiConsumer<String, DepartmentModel> modelConsumer) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            final var departmentItems = event.getTableView().getItems();
            final int rowPosition = event.getTablePosition().getRow();

            final DepartmentModel model = departmentItems.get(rowPosition);
            if (model == null)
                return;

            modelConsumer.accept(event.getNewValue(), model);
            Database.update(ServiceType.DEPARTMENT, model.getAsPojo());
        });
    }

    private Label getLabel() {
        final Label label = new Label("There are no departments.");
        label.getStyleClass().add("empty-label");
        return label;
    }

}
