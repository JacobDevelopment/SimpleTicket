package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeModel {

    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty departmentId;
    private final IntegerProperty companyId;
    private final StringProperty title;
    private final StringProperty email;
    private final StringProperty created;

    public EmployeeModel(int id,
                         String firstName,
                         String lastName,
                         int departmentId,
                         int companyId,
                         String title,
                         String email,
                         String created) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.departmentId = new SimpleIntegerProperty(departmentId);
        this.companyId = new SimpleIntegerProperty(companyId);
        this.title = new SimpleStringProperty(title);
        this.email = new SimpleStringProperty(email);
        this.created = new SimpleStringProperty(created);
    }

    public EmployeeModel(final Employee employee) {
        this(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartmentId(),
                employee.getCompanyId(),
                employee.getTitle(),
                employee.getEmail(),
                employee.getCreatedOn()
        );
    }

    public int getId() {
        return this.id.getValue();
    }

    public String getFirstName() {
        return this.firstName.getValueSafe();
    }

    public String getLastName() {
        return this.lastName.getValueSafe();
    }

    public int getDepartmentId() {
        return this.departmentId.getValue();
    }

    public int getCompanyId() {
        return this.companyId.getValue();
    }

    public String getTitle() {
        return this.title.getValueSafe();
    }

    public String getEmail() {
        return this.email.getValueSafe();
    }

    public String getCreated() {
        return this.created.getValueSafe();
    }

    public void setFirstName(final String firstName) {
        this.firstName.setValue(firstName);
    }

    public void setLastName(final String lastName) {
        this.lastName.setValue(lastName);
    }

    public void setDepartment(final int id) {
        this.departmentId.setValue(id);
    }

    public void setCompany(final int id) {
        this.companyId.setValue(id);
    }

    public void setTitle(final String title) {
        this.title.setValue(title);
    }

    public void setEmail(final String email) {
        this.email.setValue(email);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public IntegerProperty departmentIdProperty() {
        return departmentId;
    }

    public IntegerProperty companyIdProperty() {
        return companyId;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty createdProperty() {
        return created;
    }

    public Employee getAsPojo() {
        return new Employee(
                getId(),
                getFirstName(),
                getLastName(),
                getDepartmentId(),
                getCompanyId(),
                getTitle(),
                getEmail(),
                getCreated()
        );
    }


}

