package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.Settings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingsModel {

    private final BooleanProperty archiveTickets;
    private final BooleanProperty autoArchiveTickets;
    private final BooleanProperty autoDeleteTickets;
    private final StringProperty autoDeleteDate;
    private final StringProperty autoArchiveDate;
    private final StringProperty archiveDirectory;
    private final BooleanProperty dontShowConfirmation;
    private final BooleanProperty dontShowTicketConfirmation;

    public SettingsModel(boolean archiveTickets, boolean autoArchiveTickets,
                         boolean autoDeleteTickets, String autoDeleteDate,
                         String autoArchiveDate, String archiveDirectory,
                         boolean dontShowConfirmation, boolean dontShowTicketConfirmation) {
        this.archiveTickets = new SimpleBooleanProperty(archiveTickets);
        this.autoArchiveTickets = new SimpleBooleanProperty(autoArchiveTickets);
        this.autoDeleteTickets = new SimpleBooleanProperty(autoDeleteTickets);
        this.autoDeleteDate = new SimpleStringProperty(autoDeleteDate);
        this.autoArchiveDate = new SimpleStringProperty(autoArchiveDate);
        this.archiveDirectory = new SimpleStringProperty(archiveDirectory);
        this.dontShowConfirmation = new SimpleBooleanProperty(dontShowConfirmation);
        this.dontShowTicketConfirmation = new SimpleBooleanProperty(dontShowTicketConfirmation);
    }

    public SettingsModel(Settings settings) {
        this(
                settings.getArchiveTickets(),
                settings.getAutoArchiveTickets(),
                settings.getAutoDeleteTickets(),
                settings.getAutoDeleteDate(),
                settings.getAutoArchiveDate(),
                settings.getArchiveDirectory(),
                settings.getDontShowConfirmation(),
                settings.getDontShowConfirmationTickets()
        );
    }

    public boolean isArchiveTickets() {
        return archiveTickets.get();
    }

    public BooleanProperty archiveTicketsProperty() {
        return archiveTickets;
    }

    public void setArchiveTickets(boolean archiveTickets) {
        this.archiveTickets.set(archiveTickets);
    }

    public boolean isAutoArchiveTickets() {
        return autoArchiveTickets.get();
    }

    public BooleanProperty autoArchiveTicketsProperty() {
        return autoArchiveTickets;
    }

    public void setAutoArchiveTickets(boolean autoArchiveTickets) {
        this.autoArchiveTickets.set(autoArchiveTickets);
    }

    public boolean isAutoDeleteTickets() {
        return autoDeleteTickets.get();
    }

    public BooleanProperty autoDeleteTicketsProperty() {
        return autoDeleteTickets;
    }

    public void setAutoDeleteTickets(boolean autoDeleteTickets) {
        this.autoDeleteTickets.set(autoDeleteTickets);
    }

    public String getAutoDeleteDate() {
        return autoDeleteDate.get();
    }

    public StringProperty autoDeleteDateProperty() {
        return autoDeleteDate;
    }

    public void setAutoDeleteDate(String autoDeleteDate) {
        this.autoDeleteDate.set(autoDeleteDate);
    }

    public String getAutoArchiveDate() {
        return autoArchiveDate.get();
    }

    public StringProperty autoArchiveDateProperty() {
        return autoArchiveDate;
    }

    public void setAutoArchiveDate(String autoArchiveDate) {
        this.autoArchiveDate.set(autoArchiveDate);
    }

    public String getArchiveDirectory() {
        return archiveDirectory.get();
    }

    public StringProperty archiveDirectoryProperty() {
        return archiveDirectory;
    }

    public void setArchiveDirectory(String archiveDirectory) {
        this.archiveDirectory.set(archiveDirectory);
    }

    public boolean isDontShowConfirmation() {
        return dontShowConfirmation.get();
    }

    public BooleanProperty dontShowConfirmationProperty() {
        return dontShowConfirmation;
    }

    public void setDontShowConfirmation(boolean dontShowConfirmation) {
        this.dontShowConfirmation.set(dontShowConfirmation);
    }

    public boolean isDontShowTicketConfirmation() {
        return dontShowTicketConfirmation.get();
    }

    public BooleanProperty dontShowTicketConfirmationProperty() {
        return dontShowTicketConfirmation;
    }

    public void setDontShowTicketConfirmation(boolean dontShowTicketConfirmation) {
        this.dontShowTicketConfirmation.set(dontShowTicketConfirmation);
    }

    public Settings getAsPojo() {
        return new Settings(1,
                isArchiveTickets(),
                isAutoArchiveTickets(),
                isAutoDeleteTickets(),
                getAutoDeleteDate(),
                getAutoArchiveDate(),
                getArchiveDirectory(),
                isDontShowConfirmation(),
                isDontShowTicketConfirmation());
    }


    @Override
    public String toString() {
        return "SettingsModel{" +
                "archiveTickets=" + archiveTickets +
                ", autoArchiveTickets=" + autoArchiveTickets +
                ", autoDeleteTickets=" + autoDeleteTickets +
                ", autoDeleteDate=" + autoDeleteDate +
                ", autoArchiveDate=" + autoArchiveDate +
                ", archiveDirectory=" + archiveDirectory +
                ", dontShowConfirmation=" + dontShowConfirmation +
                '}';
    }
}
