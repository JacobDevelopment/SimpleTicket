package io.jacobking.simpleticket.gui.view.combobox;

import io.jacobking.simpleticket.gui.model.CompanyModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CompanyComboBox extends ComboBox<CompanyModel> {

    private final ObservableList<CompanyModel> companyList = FXCollections.observableArrayList();

    public CompanyComboBox() {
        build();
        overrideCellFactory();
        setDepartmentBoxConverter();
    }

    private void build() {
        setItems(companyList);
    }

    private void overrideCellFactory() {
        setCellFactory(new Callback<>() {
            @Override
            public ListCell<CompanyModel> call(ListView<CompanyModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CompanyModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item.getTitle());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void setDepartmentBoxConverter() {
        setConverter(new StringConverter<>() {
            @Override
            public String toString(CompanyModel object) {
                if (object == null)
                    return "";
                return object.getTitle();
            }

            @Override
            public CompanyModel fromString(String string) {
                return null;
            }
        });
    }


}
