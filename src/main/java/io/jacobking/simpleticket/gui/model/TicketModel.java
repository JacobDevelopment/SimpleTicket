package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TicketModel {

    private final StringProperty statusProperty;
    private final StringProperty priorityProperty;
    private final IntegerProperty idProperty;
    private final StringProperty subjectProperty;
    private final IntegerProperty employeeProperty;
    private final StringProperty creationProperty;

    public TicketModel(String status, String priority, int id, String subject, int employeeId, String creation) {
        this.statusProperty = new SimpleStringProperty(status);
        this.priorityProperty = new SimpleStringProperty(priority);
        this.idProperty = new SimpleIntegerProperty(id);
        this.subjectProperty = new SimpleStringProperty(subject);
        this.employeeProperty = new SimpleIntegerProperty(employeeId);
        this.creationProperty = new SimpleStringProperty(creation);
    }

    public TicketModel(final Ticket ticket) {
        this(
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getId(),
                ticket.getSubject(),
                ticket.getEmployeeId(),
                ticket.getCreatedOn()
        );
    }

    public String getStatus() {
        return statusProperty.getValueSafe();
    }

    public void setStatus(final String status) {
        this.statusProperty.setValue(status);
    }

    public String getPriority() {
        return priorityProperty.getValueSafe();
    }

    public void setPriority(final String priority) {
        this.priorityProperty.setValue(priority);
    }

    public int getId() {
        return idProperty.getValue();
    }

    public void setSubject(final String subject) {
        this.subjectProperty.setValue(subject);
    }

    public String getSubject() {
        return subjectProperty.getValue();
    }

    public int getEmployee() {
        return employeeProperty.getValue();
    }

    public void setEmployee(final int employee) {
        this.employeeProperty.setValue(employee);
    }

    public String getCreation() {
        return creationProperty.getValueSafe();
    }

    public String getStatusProperty() {
        return statusProperty.get();
    }

    public StringProperty statusProperty() {
        return statusProperty;
    }

    public String getPriorityProperty() {
        return priorityProperty.get();
    }

    public StringProperty priorityProperty() {
        return priorityProperty;
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    public String getSubjectProperty() {
        return subjectProperty.get();
    }

    public StringProperty subjectProperty() {
        return subjectProperty;
    }

    public int getEmployeeProperty() {
        return employeeProperty.get();
    }

    public IntegerProperty employeeProperty() {
        return employeeProperty;
    }

    public String getCreationProperty() {
        return creationProperty.get();
    }

    public StringProperty creationProperty() {
        return creationProperty;
    }

    public Ticket getAsPojo() {
        return new Ticket(
                getId(),
                getSubject(),
                getCreation(),
                getStatus(),
                getPriority(),
                getEmployee()
        );
    }
}
