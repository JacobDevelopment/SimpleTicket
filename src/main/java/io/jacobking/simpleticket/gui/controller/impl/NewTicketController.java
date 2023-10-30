package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.EmployeeComboBox;
import io.jacobking.simpleticket.gui.view.combobox.PriorityComboBox;
import io.jacobking.simpleticket.gui.view.combobox.StatusComboBox;
import io.jacobking.simpleticket.object.PriorityType;
import io.jacobking.simpleticket.object.StatusType;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import io.jacobking.simpleticket.utility.DateUtil;
import io.jacobking.simpleticket.utility.MiscUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTicketController extends Controller {

    @FXML private TextField subjectField;
    @FXML private TextField creationField;
    @FXML private TextField employeeIdField;

    @FXML private Button createButton;

    @FXML private StatusComboBox statusBox;
    @FXML private PriorityComboBox priorityBox;
    @FXML private EmployeeComboBox employeeBox;

    private final TicketProctor ticketProctor;
    private final EmployeeProctor employeeProctor;

    public NewTicketController() {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
        this.employeeProctor = Proctor.getInstance().employee();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationField.setText(DateUtil.now());
        createButton.disableProperty().bind(subjectField.textProperty().isEmpty());

        statusBox.getSelectionModel().selectFirst();
        priorityBox.getSelectionModel().selectFirst();

        employeeBox.setItems(employeeProctor.getModelList());

        employeeIdField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                return;

            final int id = MiscUtil.isNumber(newValue);
            if (id == -1) {
                employeeBox.getSelectionModel().clearSelection();
                return;
            }

            for (final EmployeeModel model : employeeProctor.getModelList()) {
                final int modelId = model.getId();
                if (modelId == id) {
                    employeeBox.getSelectionModel().select(model);
                    break;
                }
                employeeBox.getSelectionModel().clearSelection();
            }
        }));

    }

    @FXML
    private void onCreate() {
        final String subject = subjectField.getText();
        final StatusType status = getStatusType();
        final PriorityType priority = getPriorityType();
        final int employeeId = 1;
        final String creation = creationField.getText();

        ticketProctor.create(new Ticket()
                .setStatus(status.name())
                .setPriority(priority.name())
                .setCreatedOn(creation)
                .setEmployeeId(employeeId)
                .setSubject(subject)
        );

        getNavigation().close(Route.NEW_TICKET);
    }

    @FXML
    private void onClearFields() {
        statusBox.getSelectionModel().clearSelection();
        priorityBox.getSelectionModel().clearSelection();
        subjectField.clear();
        employeeIdField.clear();
    }

    @FXML
    private void onClose() {
        getNavigation().close(Route.NEW_TICKET);
    }


    private StatusType getStatusType() {
        return statusBox.getSelectionModel().getSelectedItem();
    }

    private PriorityType getPriorityType() {
        return priorityBox.getSelectionModel().getSelectedItem();
    }

}
