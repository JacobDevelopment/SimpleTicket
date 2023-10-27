package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketController extends Controller {

    private final TicketProctor ticketProctor;

    @FXML private Label openTicketsLabel;
    @FXML private Label resolvedTicketsLabel;
    @FXML private Label inProgressTicketsLabel;
    @FXML private Label pausedTicketsLabel;

    @FXML private TableView<TicketModel> ticketTable;
    @FXML private TableColumn<TicketModel, String> subjectColumn;
    @FXML private TableColumn<TicketModel, String> employeeColumn;
    @FXML private TableColumn<TicketModel, String> statusColumn;
    @FXML private TableColumn<TicketModel, String> priorityColumn;

    public TicketController() {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        ticketTable.setItems(ticketProctor.getModelList());

        configureLabels();
    }

    @FXML
    private void onCreateTicket() {
        getNavigation().display(Route.NEW_TICKET, true);
    }

    @FXML
    private void onDeleteTicket() {
        final TicketModel model = ticketTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            ticketProctor.delete(model.getId());
        }
    }

    private void configureTable() {
        ticketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        subjectColumn.setCellValueFactory(data -> data.getValue().subjectProperty());
        employeeColumn.setCellValueFactory(data -> {
            final int id = data.getValue().getEmployee();
            final Employee employee = getResolvedEmployee(id);
            if (employee != null) {
                return new SimpleStringProperty(String.format(
                        "%s %s", employee.getFirstName(), employee.getLastName())
                );
            }
            return data.getValue().employeeProperty().asString();
        });
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        priorityColumn.setCellValueFactory(data -> data.getValue().priorityProperty());
    }

    private Employee getResolvedEmployee(final int id) {
        return Database.fetch(ServiceType.EMPLOYEE, id);
    }

    private void configureLabels() {
        final var openTicketsList = ticketProctor.getOpenTickets();
        updateLabel(openTicketsLabel, openTicketsList.size());
        addListenerForFilteredList(openTicketsList, openTicketsLabel);

        final var resolvedTicketList = ticketProctor.getResolvedTickets();
        updateLabel(resolvedTicketsLabel, resolvedTicketList.size());
        addListenerForFilteredList(resolvedTicketList, resolvedTicketsLabel);

        final var inProgressTicketsList = ticketProctor.getInProgressTickets();
        updateLabel(inProgressTicketsLabel, inProgressTicketsList.size());
        addListenerForFilteredList(inProgressTicketsList, inProgressTicketsLabel);

        final var pausedTicketList = ticketProctor.getPausedTickets();
        updateLabel(pausedTicketsLabel, pausedTicketList.size());
        addListenerForFilteredList(pausedTicketList, pausedTicketsLabel);
    }

    private void addListenerForFilteredList(final FilteredList<TicketModel> filteredList, final Label label) {
        filteredList.addListener((ListChangeListener<? super TicketModel>) change -> {
            while (change.next()) {
                final int count = filteredList.size();
                updateLabel(label, count);
            }
        });
    }

    private void updateLabel(final Label label, final int count) {
        label.setText(String.valueOf(count));
    }


}
