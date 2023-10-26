package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Company;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCompanyController extends Controller {
    private final CompanyProctor companyProctor;

    @FXML private TextField nameField;
    @FXML private TextField abbreviationField;
    @FXML private Button createButton;

    public NewCompanyController() {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(nameField.textProperty().isEmpty());
    }

    @FXML
    private void onCreate() {
        final String abbrev = abbreviationField.getText().isEmpty() ? "N/A" : abbreviationField.getText();
        final Company company = new Company()
                .setTitle(nameField.getText())
                .setAbbreviation(abbrev);

        companyProctor.create(company);
        getNavigation().close(Route.NEW_COMPANY);
    }

    @FXML
    private void onClearFields() {
        nameField.clear();
        abbreviationField.clear();
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.NEW_COMPANY);
    }
}
