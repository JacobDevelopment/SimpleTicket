package io.jacobking.simpleticket.gui.controller.impl;

import io.jacobking.simpleticket.core.Config;
import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.alert.Alerts;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.model.SettingsModel;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.tables.pojos.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.controlsfx.control.ToggleSwitch;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends Controller {

    private final SettingsModel model;

    @FXML private ToggleSwitch archiveSwitch;
    @FXML private ToggleSwitch autoArchiveSwitch;
    @FXML private TextField archiveDateField;
    @FXML private TextField archiveDirectoryField;

    @FXML private ToggleSwitch autoDeleteSwitch;
    @FXML private TextField deletionDateField;

    @FXML private ToggleSwitch confirmationSwitch;
    @FXML private Button directoryButton;

    public SettingsController() {
        super(Navigation.getInstance());
        this.model = Proctor.getInstance().settings().fetch();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        archiveSwitch.setSelected(model.isArchiveTickets());
        autoArchiveSwitch.setSelected(model.isAutoArchiveTickets());
        archiveDateField.setText(model.getAutoArchiveDate());

        final Tooltip tooltip = new Tooltip();
        tooltip.setText(model.getArchiveDirectory());
        archiveDirectoryField.setText(model.getArchiveDirectory());
        archiveDirectoryField.setTooltip(tooltip);

        autoDeleteSwitch.setSelected(model.isAutoDeleteTickets());
        deletionDateField.setText(model.getAutoDeleteDate());
        confirmationSwitch.setSelected(model.isDontShowConfirmation());

        setBindings();
    }

    @FXML
    private void onSaveChanges() {
        model.setArchiveTickets(archiveSwitch.isSelected());
        model.setAutoArchiveTickets(autoArchiveSwitch.isSelected());
        model.setArchiveDirectory(archiveDirectoryField.getText());
        model.setAutoArchiveDate(archiveDateField.getText());
        model.setAutoDeleteDate(deletionDateField.getText());
        model.setAutoDeleteTickets(autoDeleteSwitch.isSelected());
        model.setDontShowConfirmation(confirmationSwitch.isSelected());

        final Settings pojo = model.getAsPojo();
        if (!Database.update(ServiceType.SETTINGS, pojo)) {
            Alerts.showError("Failed to update settings.", "An unknown error occurred. Try again.");
            return;
        }

        Alerts.showInfo("Settings updated successfully.", "");
    }

    @FXML
    private void onUndoChanges() {
        model.setArchiveTickets(model.isArchiveTickets());
        model.setAutoArchiveTickets(model.isAutoArchiveTickets());
        model.setArchiveDirectory(model.getArchiveDirectory());
        model.setAutoArchiveDate(model.getAutoArchiveDate());
        model.setAutoDeleteDate(model.getAutoDeleteDate());
        model.setAutoDeleteTickets(model.isAutoDeleteTickets());
        model.setDontShowConfirmation(model.isDontShowConfirmation());

        final Settings pojo = model.getAsPojo();
        if (!Database.update(ServiceType.SETTINGS, pojo)) {
            Alerts.showError("Failed to update settings.", "An unknown error occurred. Try again.");
            return;
        }

        initialize(null, null);
        Alerts.showInfo("Settings reverted back to original states.", "");
    }

    @FXML
    private void onUseFactoryDefaults() {
        Alerts.showError("This feature is not implemented yet.", "");
    }

    @FXML
    private void onArchiveDirectory() {
        final DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Ticket Archive directory.");

        final File file = new File(Config.Commons.DIRECTORY);
        chooser.setInitialDirectory(file);

        final Window window = directoryButton.getScene().getWindow();
        final File selectedDirectory = chooser.showDialog(window);
        if (selectedDirectory != null) {
            final String path = selectedDirectory.getPath();
            archiveDirectoryField.setText(path);
        }
    }

    private void setBindings() {
        autoArchiveSwitch.disableProperty().bind(archiveSwitch.selectedProperty().not());
        archiveDateField.disableProperty().bind(archiveSwitch.selectedProperty().not());
        archiveDirectoryField.disableProperty().bind(archiveSwitch.selectedProperty().not());
        directoryButton.disableProperty().bind(archiveSwitch.selectedProperty().not());
        archiveDateField.disableProperty().bind(autoArchiveSwitch.selectedProperty().not());
        deletionDateField.disableProperty().bind(autoDeleteSwitch.selectedProperty().not());
    }
}
