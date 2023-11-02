package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.core.SimpleTicket;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends Controller {

    private final ObjectProperty<AnchorPane> activePane = new SimpleObjectProperty<>();
    private final ObjectProperty<Button> activeButton = new SimpleObjectProperty<>();

    @FXML private Button homeButton;
    @FXML private Button ticketsButton;
    @FXML private Button companyButton;
    @FXML private Button departmentButton;
    @FXML private Button employeeButton;
    @FXML private Button settingsButton;

    @FXML private AnchorPane home;
    @FXML private AnchorPane tickets;
    @FXML private AnchorPane company;
    @FXML private AnchorPane department;
    @FXML private AnchorPane employee;
    @FXML private AnchorPane settings;

    public DashboardController() {
        super(Navigation.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activePane.addListener(((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                disablePane(oldValue);
            }

            if (newValue != null) {
                enablePane(newValue);
            }
        }));

        activeButton.addListener(((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                removeButton(oldValue);
            }

            if (newValue != null) {
                enableButton(newValue);
            }
        }));
        activePane.set(tickets);
        activeButton.set(ticketsButton);
    }

    @FXML
    private void onHome() {
        activeButton.setValue(homeButton);
        activePane.setValue(home);
    }

    @FXML
    private void onTickets() {
        activeButton.setValue(ticketsButton);
        activePane.setValue(tickets);
    }

    @FXML
    private void onCompany() {
        activeButton.setValue(companyButton);
        activePane.setValue(company);
    }

    @FXML
    private void onDepartment() {
        activeButton.setValue(departmentButton);
        activePane.setValue(department);
    }

    @FXML
    private void onEmployee() {
        activeButton.setValue(employeeButton);
        activePane.setValue(employee);
    }

    @FXML
    private void onSettings() {
        activeButton.setValue(settingsButton);
        activePane.setValue(settings);
    }

    @FXML
    private void onExit() {
        SimpleTicket.getInstance().shutdown();
    }

    private void enableButton(final Button button) {
        button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    private void removeButton(final Button button) {
        button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    }

    private void enablePane(final AnchorPane pane) {
        pane.setVisible(true);
        pane.setDisable(false);
    }

    private void disablePane(final AnchorPane pane) {
        pane.setVisible(false);
        pane.setDisable(true);
    }
}
