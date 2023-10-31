package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.tables.pojos.Company;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyController extends Controller {

    private final CompanyProctor companyProctor;

    @FXML private Label companyLabel;

    @FXML private TableView<CompanyModel> companyTable;
    @FXML private TableColumn<CompanyModel, String> nameColumn;
    @FXML private TableColumn<CompanyModel, String> abbreviationColumn;
    @FXML private TableColumn<CompanyModel, String> descriptionColumn;
    @FXML private TableColumn<CompanyModel, String> createdOnColumn;

    @FXML private SVGPath searchIcon;
    @FXML private TextField searchField;

    public CompanyController() {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyLabel.setText(String.valueOf(companyProctor.getModelList().size()));
        addLabelListener();

        configureTable();

        searchIcon.setOnMousePressed(event -> onSearch());
    }

    @FXML
    private void onCreateCompany() {
        getNavigation().display(Route.COMPANY_PORTAL, true);
    }

    @FXML
    private void onDeleteCompany() {
        final CompanyModel model = companyTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            companyProctor.delete(model.getId());
        }
    }

    @FXML
    private void onEditCompany() {
        final CompanyModel model = companyTable.getSelectionModel().getSelectedItem();
        if (model != null) {
            getNavigation().display(Route.COMPANY_PORTAL, true, model);
        }
    }


    private void configureTable() {
        companyTable.setItems(companyProctor.getModelList());
        nameColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        abbreviationColumn.setCellValueFactory(data -> data.getValue().abbreviationProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        createdOnColumn.setCellValueFactory(data -> data.getValue().createdOnProperty());
    }

    private void addLabelListener() {
        companyProctor.getModelList().addListener((ListChangeListener<? super CompanyModel>) change -> {
            while (change.next()) {
                companyLabel.setText(String.valueOf(companyProctor.getModelList().size()));
            }
        });
    }

    // Doesn't check for duplicate entries with similar names/abbreviations.
    @FXML
    private void onSearch() {
        final String search = searchField.getText();
        if (search.isEmpty())
            return;

        companyTable.getItems().forEach(model -> {
            final String name = model.getTitle();
            if (name.contains(search)) {
                companyTable.getSelectionModel().select(model);
                return;
            }

            final String abbreviation = model.getAbbreviation();
            if (abbreviation.contains(search)) {
                companyTable.getSelectionModel().select(model);
            }

            final String description = model.getDescription();
            if (description.contains(search)) {
                companyTable.getSelectionModel().select(model);
            }
        });
    }
}
