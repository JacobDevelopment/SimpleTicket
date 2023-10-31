package io.jacobking.simpleticket.gui.controller.impl.portal;


import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.object.PriorityType;
import io.jacobking.simpleticket.object.StatusType;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketPortalController extends Controller {

    private final TicketProctor ticketProctor;

    @FXML private TextField subjectField;
    @FXML private TextArea commentArea;
    @FXML private SearchableComboBox<String> priorityBox;
    @FXML private SearchableComboBox<String> statusBox;
    @FXML private SearchableComboBox<EmployeeModel> employeeBox;

    @FXML private Button createButton;
    @FXML private Button quickCreateButton;

    public TicketPortalController() {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bindBidirectional(quickCreateButton.disableProperty());
        quickCreateButton.disableProperty().bind(subjectField.textProperty().isEmpty());
        priorityBox.setItems(PriorityType.getObservableList());
        statusBox.setItems(StatusType.getObservableList());
        configureEmployeeBox();
    }

    @FXML
    private void onCreate() {
        final String subject = subjectField.getText();
        final String comment = commentArea.getText();
        final String priority = getPriority();
        final String status = getStatus();
        final int employee = getEmployeeId();

        final Ticket ticket = new Ticket()
                .setSubject(subject)
                .setCreatedOn(DateUtil.now())
                .setPriority(priority)
                .setStatus(status)
                .setEmployeeId(employee);


        final TicketModel model = ticketProctor.createAndReturn(ticket);
        createComment(model.getId(), comment);

        getNavigation().close(Route.NEW_TICKET);
        getNavigation().display(Route.TICKET_VIEWER, true, model);
    }

    @FXML
    private void onQuickCreate() {
        final String subject = subjectField.getText();
        final String priority = "LOW";
        final String status = "OPEN";
        final int employeeId = 0;

        final Ticket ticket = new Ticket()
                .setSubject(subject)
                .setCreatedOn(DateUtil.now())
                .setPriority(priority)
                .setStatus(status)
                .setEmployeeId(employeeId);

        final TicketModel model = ticketProctor.createAndReturn(ticket);

        getNavigation().close(Route.NEW_TICKET);
        getNavigation().display(Route.TICKET_VIEWER, true, model);
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.NEW_TICKET);
    }

    private void createComment(final int ticketId, final String comment) {
        if (comment.isEmpty()) return;
        Database.insert(ServiceType.TICKET_COMMENTS, new TicketComments()
                .setComment(comment)
                .setDate(DateUtil.now())
                .setTicketId(ticketId)
        );
    }
    private void configureEmployeeBox() {
        employeeBox.setItems(Proctor.getInstance().employee().getModelList());
        employeeBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<EmployeeModel> call(ListView<EmployeeModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(EmployeeModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
                        }
                    }
                };
            }
        });

        employeeBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeModel object) {
                if (object == null)
                    return "Unknown";
                return String.format("%s %s", object.getFirstName(), object.getLastName());
            }

            @Override
            public EmployeeModel fromString(String string) {
                return null;
            }
        });
    }

    private String getPriority() {
        final String priority = priorityBox.getSelectionModel().getSelectedItem();
        if (priority == null)
            return "LOW";
        return priority;
    }

    private String getStatus() {
        final String status = statusBox.getSelectionModel().getSelectedItem();
        if (status == null)
            return "OPEN";
        return status;
    }

    private int getEmployeeId() {
        final EmployeeModel model = employeeBox.getSelectionModel().getSelectedItem();
        if (model != null)
            return model.getId();
        return 0;
    }
}
