package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.EmployeeComboBox;
import io.jacobking.simpleticket.gui.view.combobox.PriorityComboBox;
import io.jacobking.simpleticket.gui.view.combobox.StatusComboBox;
import io.jacobking.simpleticket.tables.pojos.Employee;
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.utility.DateUtil;
import io.jacobking.simpleticket.utility.MiscUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import org.jooq.impl.DSL;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static io.jacobking.simpleticket.tables.TicketComments.TICKET_COMMENTS;

public class TicketViewerController extends Controller {

    private final TicketModel model;

    @FXML private Label subjectLabel;
    @FXML private Label idLabel;
    @FXML private Label statusLabel;
    @FXML private Label priorityLabel;
    @FXML private Label employeeLabel;
    @FXML private Label dateLabel;

    @FXML private SearchableComboBox<String> statusBox;
    @FXML private SearchableComboBox<String> priorityBox;
    @FXML private SearchableComboBox<EmployeeModel> employeeBox;
    @FXML private TextField subjectField;

    @FXML private Tooltip subjectTooltip;

    @FXML private VBox commentBox;
    @FXML private TextField commentField;
    @FXML private Button postButton;

    public TicketViewerController(TicketModel model) {
        super(Navigation.getInstance());
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subjectLabel.setText(model.getSubject());
        subjectTooltip.setText(subjectLabel.getText());

        idLabel.setText(String.valueOf(model.getId()));
        statusLabel.setText(model.getStatus());
        priorityLabel.setText(model.getPriority());
        employeeLabel.setText(getResolvedEmployee(model.getEmployee()));
        dateLabel.setText(model.getCreation());

        postButton.disableProperty().bind(commentField.textProperty().isEmpty());

        configureStatusBox();
        configurePriorityBox();
        configureEmployeeBox();
        configureSubject();
    }

    @FXML
    private void onPostComment() {
        final String comment = commentField.getText();
        final Label label = new Label(comment);
        label.setWrapText(true);
        label.setStyle("-fx-text-fill: -fx-primary");

        commentBox.getChildren().add(label);

        Database.insert(ServiceType.TICKET_COMMENTS, new TicketComments()
                .setTicketId(model.getId())
                .setComment(comment)
                .setDate(DateUtil.now())
        );

        commentField.clear();
    }

    private String getResolvedEmployee(final int id) {
        final Employee employee = Database.fetch(ServiceType.EMPLOYEE, id);
        if (employee != null) {
            return String.format("%s %s", employee.getFirstName(), employee.getLastName());
        }
        return String.valueOf(id);
    }

    @FXML
    private void onUndoChanges() {

    }


    private void configureStatusBox() {
        statusBox.getItems().addAll("Open", "Paused", "Resolved", "In-Progress");
        statusBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }
            statusLabel.setText(newValue);
        }));
    }

    private void configurePriorityBox() {
        priorityBox.getItems().addAll("Low", "Medium", "High", "Emergency");
        priorityBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                return;
            priorityLabel.setText(newValue);
        }));
    }

    private void configureEmployeeBox() {
        employeeBox.setItems(Proctor.getInstance().employee().getModelList());
        employeeBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            employeeLabel.setText(String.format("%s %s", newValue.getFirstName(), newValue.getLastName()));
        }));

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

        employeeBox.setConverter(new StringConverter<EmployeeModel>() {
            @Override
            public String toString(EmployeeModel object) {
                return String.format("%s %s", object.getFirstName(), object.getLastName());
            }

            @Override
            public EmployeeModel fromString(String string) {
                return null;
            }
        });
    }

    private void configureSubject() {
        subjectField.textProperty().addListener(((observable, oldValue, newValue) -> {
            subjectLabel.setText(newValue);
        }));
    }

}
