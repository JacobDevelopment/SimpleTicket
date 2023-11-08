package io.jacobking.simpleticket.gui.controller.impl.portal;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyPortalController extends Controller {

    private final CompanyProctor companyProctor;
    private final CompanyModel model;

    @FXML private TextField companyNameField;
    @FXML private TextField companyAbbreviationField;
    @FXML private TextField companyDescriptionField;

    public CompanyPortalController(CompanyModel model) {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model != null) {
            populateFields();
        }
    }

    @FXML
    private void onSaveCompany() {
        if (model != null) {
            updateModel();
            return;
        }

        final Company company = new Company()
                .setTitle(companyNameField.getText())
                .setAbbreviation(companyAbbreviationField.getText())
                .setDescription(companyDescriptionField.getText())
                .setCreatedOn(DateUtil.nowLocalDateTime());

        companyProctor.create(company);
        getNavigation().close(Route.COMPANY_PORTAL);
    }

    @FXML
    private void onCancel() {
        getNavigation().close(Route.COMPANY_PORTAL);
    }

    private void populateFields() {
        companyNameField.setText(model.getTitle());
        companyDescriptionField.setText(model.getDescription());
        companyAbbreviationField.setText(model.getAbbreviation());
    }

    private void updateModel() {
        model.setTitle(companyNameField.getText());
        model.setDescription(companyDescriptionField.getText());
        model.setAbbreviation(companyAbbreviationField.getText());

        final Company company = model.getAsPojo();
        Database.update(ServiceType.COMPANY, company);
        getNavigation().close(Route.COMPANY_PORTAL);
    }
}
