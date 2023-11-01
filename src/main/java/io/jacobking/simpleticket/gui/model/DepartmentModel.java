package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DepartmentModel {

    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty abbreviation;
    private final StringProperty description;
    private final IntegerProperty supervisorId;
    private final IntegerProperty companyId;

    public DepartmentModel(int id, String title, String abbreviation, String description, int supervisorId, int companyId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.abbreviation = new SimpleStringProperty(abbreviation);
        this.description = new SimpleStringProperty(description);
        this.supervisorId = new SimpleIntegerProperty(supervisorId);
        this.companyId = new SimpleIntegerProperty(companyId);
    }

    public DepartmentModel(final Department department) {
        this(
                department.getId(),
                department.getTitle(),
                department.getAbbreviation(),
                department.getDescription(),
                department.getSupervisorId(),
                department.getCompanyId()
        );
    }

    public int getId() {
        return this.id.getValue();
    }

    public String getTitle() {
        return this.title.getValueSafe();
    }

    public String getAbbreviation() {
        return this.abbreviation.getValueSafe();
    }

    public int getSupervisorId() {
        return this.supervisorId.getValue();
    }

    public int getCompanyId() {
        return this.companyId.getValue();
    }

    public void setTitle(final String title) {
        this.title.setValue(title);
    }

    public void setAbbreviation(final String abbreviation) {
        this.abbreviation.setValue(abbreviation);
    }

    public void setSupervisorId(final int id) {
        this.supervisorId.setValue(id);
    }

    public void setCompanyId(final int id) {
        this.companyId.setValue(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty abbreviationProperty() {
        return abbreviation;
    }

    public IntegerProperty supervisorIdProperty() {
        return supervisorId;
    }

    public IntegerProperty companyIdProperty() {
        return companyId;
    }

    public String getDescription() {
        return description.getValueSafe();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public Department getAsPojo() {
        return new Department(
                getId(),
                getTitle(),
                getAbbreviation(),
                getDescription(),
                getSupervisorId(),
                getCompanyId()
        );
    }
}
