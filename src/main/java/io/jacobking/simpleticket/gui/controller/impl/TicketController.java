package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.table.TicketTable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketController extends Controller {

    private final TicketProctor ticketProctor;
    private final EmployeeProctor employeeProctor;

    @FXML private TicketTable ticketTable;

    public TicketController() {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
        this.employeeProctor = Proctor.getInstance().employee();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ticketTable.setItems(ticketProctor.getModelList());
    }

    @FXML
    private void onCreateTicket() {
        getNavigation().display(Route.NEW_TICKET, true, ticketProctor, employeeProctor);
    }

    @FXML
    private void onOpenTicket() {
        final TicketModel model = ticketTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            getNavigation().display(Route.TICKET_VIEWER, true, model);
        }
    }

    @FXML
    private void onDeleteTicket() {
        final TicketModel model = ticketTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            ticketProctor.delete(model.getId());
        }
    }

    @FXML
    private void onQuickUpdate() {
        final TicketModel model = ticketTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            getNavigation().display(Route.QUICK_UPDATE, true, model);
        }
    }
}
