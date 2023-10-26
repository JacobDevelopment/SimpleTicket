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
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.utility.DateUtil;
import io.jacobking.simpleticket.utility.MiscUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jooq.impl.DSL;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static io.jacobking.simpleticket.tables.TicketComments.TICKET_COMMENTS;

public class TicketViewerController extends Controller {

    private final Insets vboxInsets = new Insets(5, 5, 5, 5);

    private final TicketProctor ticketProctor;

    private final TicketModel model;

    @FXML private TextField subjectField;
    @FXML private TextField idField;
    @FXML private TextField createdField;
    @FXML private TextField employeeIdField;

    @FXML private PriorityComboBox priorityBox;
    @FXML private StatusComboBox statusBox;
    @FXML private EmployeeComboBox employeeBox;

    @FXML private VBox scrollVBox;
    @FXML private ScrollPane commentsScrollPane;
    @FXML private TextField commentField;
    @FXML private Button postButton;

    public TicketViewerController(TicketModel model) {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subjectField.setText(model.getSubject());
        createdField.setText(model.getCreation());
        idField.setText(String.valueOf(model.getId()));
        postButton.disableProperty().bind(commentField.textProperty().isEmpty());
        configureBoxes();
        preloadComments();
    }

    @FXML
    private void onSaveTicket() {
        model.setStatus(getStatus());
        model.setPriority(getPriority());
        model.setEmployee(getEmployee());

        Database.update(ServiceType.TICKET, model.getAsPojo());
        getNavigation().close(Route.TICKET_VIEWER);
    }

    @FXML
    private void onUndoChanges() {
        configureStatusBox();
        configureEmployeeBox();
        configurePriorityBox();
    }

    @FXML
    private void onDeleteTicket() {
        ticketProctor.delete(model.getId());
        getNavigation().close(Route.TICKET_VIEWER);
    }

    @FXML
    private void onExit() {
        getNavigation().close(Route.TICKET_VIEWER);
    }

    @FXML
    private void onPostComment() {
        final String comment = commentField.getText();
        final String date = DateUtil.now();
        final Label label = getCommentLabel(date, comment);
        insertComment(comment, date);
        scrollVBox.getChildren().add(label);
        commentField.clear();
    }

    private void insertComment(final String comment, final String date) {
        Database.insert(ServiceType.TICKET_COMMENTS, new TicketComments()
                .setComment(comment)
                .setDate(date)
                .setTicketId(model.getId())
        );
    }

    private void configureBoxes() {
        configurePriorityBox();
        configureStatusBox();
        configureEmployeeBox();
    }

    private void configurePriorityBox() {
        priorityBox.getItems().forEach(type -> {
            final String descriptor = type.getDescriptor();
            if (descriptor.equalsIgnoreCase(model.getPriority())) {
                priorityBox.getSelectionModel().select(type);
            }
        });
    }

    private void configureStatusBox() {
        statusBox.getItems().forEach(type -> {
            if (type.getDescriptor().equalsIgnoreCase(model.getStatus())) {
                statusBox.getSelectionModel().select(type);
            }
        });
    }

    private void configureEmployeeBox() {
        configureIdField();
        employeeBox.setItems(Proctor.getInstance().employee().getModelList());

        employeeBox.getItems().forEach(model -> {
            final int modelId = model.getId();
            if (modelId == model.getId()) {
                employeeBox.getSelectionModel().select(model);
                employeeIdField.setText(String.valueOf(modelId));
                return;
            }
            employeeBox.getSelectionModel().clearSelection();
        });

        employeeBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            final int id = newValue.getId();
            employeeIdField.setText(String.valueOf(id));
        }));
    }

    private void configureIdField() {
        employeeIdField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }

            final int id = MiscUtil.isNumber(newValue);
            if (id == -1) {
                employeeBox.getSelectionModel().clearSelection();
                return;
            }

            for (final EmployeeModel model : Proctor.getInstance().employee().getModelList()) {
                final int modelId = model.getId();
                if (modelId == id) {
                    employeeBox.getSelectionModel().select(model);
                    break;
                }
                employeeBox.getSelectionModel().clearSelection();
            }
        }));
    }

    private void preloadComments() {
        final int ticketId = model.getId();
        final List<TicketComments> comments = Database.fetchAll(
                ServiceType.TICKET_COMMENTS,
                DSL.condition(TICKET_COMMENTS.TICKET_ID.eq(ticketId))
        );

        if (comments.isEmpty())
            return;

        comments.forEach(comment -> {
            final String commentText = comment.getComment();
            final String date = comment.getDate();

            final Label label = getCommentLabel(date, commentText);
            scrollVBox.getChildren().add(label);
        });
    }

    private Label getCommentLabel(final String date, final String comment) {
        final Label label = new Label(String.format("[%s]: %s", date, comment));
        label.setWrapText(true);
        VBox.setMargin(label, vboxInsets);
        return label;
    }

    private String getStatus() {
        return statusBox.getSelectionModel()
                .getSelectedItem()
                .getDescriptor();
    }

    private String getPriority() {
        return priorityBox.getSelectionModel()
                .getSelectedItem()
                .getDescriptor();
    }

    private int getEmployee() {
        return employeeBox.getSelectionModel()
                .getSelectedItem()
                .getId();
    }
}
