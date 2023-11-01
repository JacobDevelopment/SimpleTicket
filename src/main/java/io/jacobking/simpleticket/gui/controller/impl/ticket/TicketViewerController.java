package io.jacobking.simpleticket.gui.controller.impl.ticket;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceDispatcher;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;
import io.jacobking.simpleticket.gui.model.CommentModel;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.view.combobox.EmployeeComboBox;
import io.jacobking.simpleticket.gui.view.combobox.PriorityComboBox;
import io.jacobking.simpleticket.gui.view.combobox.StatusComboBox;
import io.jacobking.simpleticket.object.PriorityType;
import io.jacobking.simpleticket.object.StatusType;
import io.jacobking.simpleticket.tables.pojos.Employee;
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.utility.DateUtil;
import io.jacobking.simpleticket.utility.MiscUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import org.jooq.impl.DSL;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static io.jacobking.simpleticket.tables.TicketComments.TICKET_COMMENTS;

public class TicketViewerController extends Controller {

    private static final int LIST_WIDTH = 766;
    private static final int LIST_PADDING = (LIST_WIDTH - 25);

    private final TicketProctor ticketProctor;
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

    @FXML private ListView<CommentModel> commentList;
    @FXML private TextField commentField;
    @FXML private Button postButton;

    public TicketViewerController(TicketModel model) {
        super(Navigation.getInstance());
        this.ticketProctor = Proctor.getInstance().ticket();
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
        configureCommentList();
    }

    @FXML
    private void onPostComment() {
        final CommentModel model = new CommentModel(commentField.getText());
        commentList.getItems().add(model);
    }

    @FXML
    private void onSaveTicket() {
        model.setEmployee(getEmployeeId());
        model.setPriority(getPriority());
        model.setStatus(getStatus());
        model.setSubject(getSubject());
        Database.update(ServiceType.TICKET, model.getAsPojo());
        getNavigation().close(Route.TICKET_VIEWER);
    }

    @FXML
    private void onUndoChanges() {
        subjectLabel.setText(model.getSubject());
        subjectField.setText(model.getSubject());
        statusBox.getSelectionModel().select(model.getStatus());
        priorityBox.getSelectionModel().select(model.getPriority());
    }

    @FXML
    private void onDeleteTicket() {
        final int ticketId = model.getId();
        ticketProctor.delete(ticketId);
        getNavigation().close(Route.TICKET_VIEWER);
    }

    private int getEmployeeId() {
        final EmployeeModel employeeModel = employeeBox.getSelectionModel().getSelectedItem();
        if (employeeModel != null)
            return employeeModel.getId();

        return model.getId();
    }

    private String getStatus() {
        final String status = statusBox.getSelectionModel().getSelectedItem();
        if (status != null)
            return status;
        return model.getStatus();
    }

    private String getPriority() {
        final String priority = priorityBox.getSelectionModel().getSelectedItem();
        if (priority != null)
            return priority;
        return model.getPriority();
    }

    private String getSubject() {
        final String subject = subjectField.getText();
        if (subject == null || subject.isEmpty())
            return model.getSubject();
        return subject;
    }

    private String getResolvedEmployee(final int id) {
        final Employee employee = Database.fetch(ServiceType.EMPLOYEE, id);
        if (employee != null) {
            return String.format("%s %s", employee.getFirstName(), employee.getLastName());
        }
        return String.valueOf(id);
    }

    private void configureStatusBox() {
        statusBox.setItems(StatusType.getObservableList());
        statusBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }
            statusLabel.setText(newValue);
        }));
    }

    private void configurePriorityBox() {
        priorityBox.setItems(PriorityType.getObservableList());
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

        employeeBox.setConverter(new StringConverter<>() {
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

    private void configureCommentList() {
        commentList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CommentModel> call(ListView<CommentModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CommentModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            final VBox box = new VBox(5);
                            final Text date = new Text(item.getDate());
                            date.setFont(Font.font(null, FontWeight.BOLD, 10));

                            final Text comment = new Text(item.getComment());
                            comment.setWrappingWidth(LIST_PADDING);

                            box.getChildren().addAll(date, comment);
                            setGraphic(box);
                        }
                    }

                };
            }
        });
    }

}
