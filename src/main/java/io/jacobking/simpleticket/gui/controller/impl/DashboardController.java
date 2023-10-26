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

    private final ObjectProperty<MenuModel> active = new SimpleObjectProperty<>();


    private MenuModel ticketModel;
    private MenuModel managementModel;

    @FXML private Pane ticketPane;
    @FXML private Pane managementPane;

    @FXML private AnchorPane ticketAnchor;
    @FXML private AnchorPane managementAnchor;

    @FXML private Label versionLabel;

    public DashboardController() {
        super(Navigation.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ticketModel = new MenuModel(ticketPane, ticketAnchor);
        this.managementModel = new MenuModel(managementPane, managementAnchor);

        active.setValue(ticketModel);

        active.addListener(((observable, oldValue, newValue) -> {
            if (oldValue == null) {
                newValue.enable();
                return;
            }

            oldValue.disable();
            newValue.enable();
        }));

        versionLabel.setText(Version.getCurrent());
    }


    @FXML
    private void onTickets() {
        setActive(ticketModel);
    }

    @FXML
    private void onManagement() {
        setActive(managementModel);
    }

    @FXML
    private void onAbout() {
        getNavigation().display(Route.ABOUT, true);
    }

    @FXML
    private void onSettings() {

    }

    @FXML
    private void onExit() {
        SimpleTicket.getInstance().shutdown();
    }

    private void setActive(final MenuModel model) {
        if (model == null)
            return;
        active.setValue(model);
    }

}
