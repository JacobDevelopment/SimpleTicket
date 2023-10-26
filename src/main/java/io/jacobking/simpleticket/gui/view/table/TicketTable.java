package io.jacobking.simpleticket.gui.view.table;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TicketTable extends TableView<TicketModel> {

    private final TableColumn<TicketModel, String> statusColumn = new TableColumn<>("Status");
    private final TableColumn<TicketModel, String> priorityColumn = new TableColumn<>("Priority");
    private final TableColumn<TicketModel, String> idColumn = new TableColumn<>("ID");
    private final TableColumn<TicketModel, String> subjectColumn = new TableColumn<>("Subject");
    private final TableColumn<TicketModel, String> employeeColumn  =new TableColumn<>("Employee");
    private final TableColumn<TicketModel, String> creationColumn = new TableColumn<>("Creation");


    public TicketTable() {
        setPlaceholder(getLabel());
        addColumns();
        setCellValueFactory();
    }

    @SuppressWarnings("unchecked")
    private void addColumns() {
        getColumns().addAll(statusColumn, priorityColumn, idColumn, subjectColumn, employeeColumn, creationColumn);
    }

    private void setCellValueFactory() {
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        priorityColumn.setCellValueFactory(data -> data.getValue().priorityProperty());
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asString());
        subjectColumn.setCellValueFactory(data -> data.getValue().subjectProperty());
        employeeColumn.setCellValueFactory(data -> data.getValue().idProperty().asString());
        creationColumn.setCellValueFactory(data -> data.getValue().creationProperty());
    }

    public void convertEmployeeColumn() {
        employeeColumn.setCellValueFactory(data-> {
            final Employee employee = Database.fetch(ServiceType.EMPLOYEE, data.getValue().getEmployee());
            if (employee == null)
                return data.getValue().employeeProperty().asString();
            return new SimpleStringProperty(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
        });
    }

    private Label getLabel() {
        final Label label = new Label("There are no tickets.");
        label.getStyleClass().add("empty-label");
        return label;
    }

}
