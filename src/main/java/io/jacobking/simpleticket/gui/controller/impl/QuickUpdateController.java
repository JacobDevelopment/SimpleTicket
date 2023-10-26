package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.PriorityComboBox;
import io.jacobking.simpleticket.gui.view.combobox.StatusComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class QuickUpdateController extends Controller {

    private final TicketModel model;

    @FXML private TextField subjectField;
    @FXML private StatusComboBox statusBox;
    @FXML private PriorityComboBox priorityBox;

    public QuickUpdateController(TicketModel model) {
        super(Navigation.getInstance());
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subjectField.setText(model.getSubject());

        statusBox.getItems().forEach(type -> {
            final String descriptor = type.getDescriptor();
            if (descriptor.equalsIgnoreCase(model.getStatus())) {
                statusBox.getSelectionModel().select(type);
                return;
            }
            statusBox.getSelectionModel().clearSelection();
        });

        priorityBox.getItems().forEach(type -> {
            final String descriptor = type.getDescriptor();
            if (descriptor.equalsIgnoreCase(model.getPriority())) {
                priorityBox.getSelectionModel().select(type);
                return;
            }
            priorityBox.getSelectionModel().select(type);
        });
    }

    @FXML
    private void onUpdate() {
        model.setSubject(subjectField.getText());
        model.setPriority(priorityBox.getSelectionModel().getSelectedItem().name());
        model.setStatus(statusBox.getSelectionModel().getSelectedItem().name());

        Database.update(ServiceType.TICKET, model.getAsPojo());
        getNavigation().close(Route.QUICK_UPDATE);
    }

    @FXML
    private void onClose() {
        getNavigation().close(Route.QUICK_UPDATE);
    }
}
