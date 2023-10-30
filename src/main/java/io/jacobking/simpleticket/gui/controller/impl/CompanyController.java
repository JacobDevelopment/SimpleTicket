package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
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

    private final BooleanProperty editableState = new SimpleBooleanProperty(false);
    private final BooleanProperty editableCompany = new SimpleBooleanProperty(false);
    private final ObjectProperty<CompanyModel> companyProperty = new SimpleObjectProperty<>();

    private final CompanyProctor companyProctor;

    @FXML private Label companyLabel;

    @FXML private TableView<CompanyModel> companyTable;
    @FXML private TableColumn<CompanyModel, String> nameColumn;
    @FXML private TableColumn<CompanyModel, String> abbreviationColumn;

    @FXML private TextField companyNameField;
    @FXML private TextField companyAbbreviationField;

    @FXML private Button saveButton;
    @FXML private SVGPath searchIcon;
    @FXML private TextField searchField;

    public CompanyController() {
        super(Navigation.getInstance());
        this.companyProctor = Proctor.getInstance().company();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyLabel.setText(String.valueOf(companyProctor.getModelList().size()));
        companyNameField.disableProperty().bindBidirectional(companyAbbreviationField.disableProperty());
        companyAbbreviationField.disableProperty().bind(editableState.not());

        companyProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                companyNameField.setText(newValue.getTitle());
                companyAbbreviationField.setText(newValue.getAbbreviation());
            }
        }));

        saveButton.disableProperty().bind(companyNameField.textProperty().isEmpty()
                .or(companyAbbreviationField.textProperty().isEmpty()));

        addLabelListener();

        configureTable();

        searchIcon.setOnMousePressed(event -> onSearch());
    }

    @FXML
    private void onCreateCompany() {
        editableState.setValue(true);
        editableCompany.setValue(false);
        companyProperty.setValue(new CompanyModel(-1, null, null));
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
            editableState.setValue(true);
            editableCompany.setValue(true);
            companyProperty.setValue(model);
        }
    }

    @FXML
    private void onSaveCompany() {
        final String name = companyNameField.getText();
        final String abbreviation = companyAbbreviationField.getText();

        final CompanyModel model = companyProperty.getValue();
        model.setTitle(name);
        model.setAbbreviation(abbreviation);

        final Company company = model.getAsPojoUnsigned();
        if (editableCompany.getValue()) {
            Database.update(ServiceType.COMPANY, company);
            return;
        }

        companyProctor.create(company);
        editableCompany.setValue(null);
        editableState.setValue(false);

        companyNameField.clear();
        companyAbbreviationField.clear();
    }

    private void configureTable() {
        companyTable.setItems(companyProctor.getModelList());
        nameColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        abbreviationColumn.setCellValueFactory(data -> data.getValue().abbreviationProperty());
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
        });
    }
}
