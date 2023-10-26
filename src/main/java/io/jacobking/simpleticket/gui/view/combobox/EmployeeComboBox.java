package io.jacobking.simpleticket.gui.view.combobox;

import io.jacobking.simpleticket.gui.model.EmployeeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class EmployeeComboBox extends ComboBox<EmployeeModel> {

    private final ObservableList<EmployeeModel> employeeList = FXCollections.observableArrayList();

    public EmployeeComboBox() {
        build();
        overrideCellFactory();
        setEmployeeBoxConverter();
    }

    private void build() {
        setItems(employeeList);
    }

    private void overrideCellFactory() {
        setCellFactory(new Callback<>() {
            @Override
            public ListCell<EmployeeModel> call(ListView<EmployeeModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(EmployeeModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void setEmployeeBoxConverter() {
        setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeModel object) {
                if (object == null)
                    return "";
                return String.format("%s %s", object.getFirstName(), object.getLastName());
            }

            @Override
            public EmployeeModel fromString(String string) {
                return null;
            }
        });
    }


}
