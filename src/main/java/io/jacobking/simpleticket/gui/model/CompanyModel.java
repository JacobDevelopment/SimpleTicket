package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.Company;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompanyModel {

    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty abbreviation;

    public CompanyModel(int id, String title, String abbreviation) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.abbreviation = new SimpleStringProperty(abbreviation);
    }

    public CompanyModel(final Company company) {
        this(
                company.getId(),
                company.getTitle(),
                company.getAbbreviation()
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

    public void setTitle(final String title) {
        this.title.setValue(title);
    }

    public void setAbbreviation(final String abbreviation) {
        this.abbreviation.setValue(abbreviation);
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

    public Company getAsPojo() {
        return new Company(
                getId(),
                getTitle(),
                getAbbreviation()
        );
    }
}
