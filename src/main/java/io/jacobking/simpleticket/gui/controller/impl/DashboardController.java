package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.core.SimpleTicket;
import io.jacobking.simpleticket.core.Version;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.model.MenuModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends Controller {


    public DashboardController() {
        super(Navigation.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onTickets() {

    }

    @FXML
    private void onCompany() {

    }

    @FXML
    private void onDepartment() {

    }

    @FXML
    private void onEmployee() {

    }

    @FXML
    private void onSettings() {

    }

    @FXML
    private void onExit() {
        SimpleTicket.getInstance().shutdown();
    }
}
